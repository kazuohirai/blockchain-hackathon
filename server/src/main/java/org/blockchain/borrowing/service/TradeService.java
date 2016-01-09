package org.blockchain.borrowing.service;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.blockchain.borrowing.domain.Trade;
import org.blockchain.borrowing.domain.User;
import org.blockchain.borrowing.repository.TradeRepository;
import org.blockchain.borrowing.repository.UserRepository;
import org.blockchain.borrowing.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TradeService {

    private static final Logger LOG = Logger.getLogger(TradeService.class);

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Trade findOne(String tranNo) {
        return tradeRepository.findOne(tranNo);
    }

    public List<Trade> listByBorrowerAndStatues(long userId, Collection<Trade.Status> statues) {

        List<Trade> trades = tradeRepository.findByBorrowerAndStatusIn(userId, statues);
        return FluentIterable.from(trades).transform(updateUser).toList();
    }

    public List<Trade> listByLenderAndStatues(long userId, Collection<Trade.Status> statues) {
        List<Trade> trades = tradeRepository.findByLenderAndStatusIn(userId, statues);
        return FluentIterable.from(trades).transform(updateUser).toList();
    }

    public List<Trade> listByFriends(long userId) {
        User user = userService.findById(userId);
        List<User> others = userRepository.findByIdNot(userId);
        List<Long> otherIds = new ArrayList<>();
        for (User u : others) {
            otherIds.add(u.getId());
        }

        List<Trade> trades = tradeRepository.findByBorrowerInAndStatusIn(otherIds, Collections.singleton(Trade.Status.INIT));
        return FluentIterable.from(trades).transform(updateUser).toList();
    }

    /**
     * post a trade
     *
     * @param trade
     * @return
     */
    public Trade postTrade(Trade trade) {
        ValidateUtils.notNulls(trade.getBorrower(), trade.getAmount(), trade.getInterest());

        trade.setTradeNo(UUID.randomUUID().toString());
        trade.setCreateTime(new Date());

        trade = tradeRepository.save(trade);
        return trade;
    }

    /**
     * borrow a trade
     *
     * @param userId lender id
     * @param trade
     * @return
     */
    public Trade borrowTrade(long userId, Trade trade) {
        LOG.info(String.format("borrow trade of trade %s, user id %s", trade, userId));
        User currentUser = userService.findById(userId);
        Validate.isTrue(userId != trade.getBorrower());
//        Validate.isTrue(trade.getStatus().equals(Trade.Status.INIT));

        userService.deduct(currentUser, trade.getAmount());

        trade.setLender(currentUser.getId());
        trade.setStatus(Trade.Status.ING);
        trade = tradeRepository.save(trade);

        return trade;
    }

    /**
     * repay a trade
     *
     * @param userId borrow id
     * @param trade  trade
     * @return
     */
    public Trade repayTrade(long userId, Trade trade) {
        LOG.info(String.format("repay trade of %s", trade));
        Validate.isTrue(userId == trade.getBorrower());
//        Validate.isTrue(trade.getStatus().equals(Trade.Status.ING));

        User borrowUser = userService.findById(trade.getBorrower());
        User lenderUser = userService.findById(trade.getLender());
        BigDecimal money = trade.getAmount();

        userService.recharge(borrowUser, money);
        userService.deduct(lenderUser, money);

        trade.setStatus(Trade.Status.COM);
        trade.setActualRepayDate(new Date());
        trade = tradeRepository.save(trade);

        return trade;
    }

    private Function<Trade, Trade> updateUser = new Function<Trade, Trade>() {
        @Override
        public Trade apply(Trade trade) {
            User borrow = userRepository.findOne(trade.getBorrower());

            if (trade.getLender() != null) {
                User lender = userRepository.findOne(trade.getLender());
                trade.setLenderUser(lender);
            }

            trade.setBorrowerUser(borrow);
            return trade;
        }
    };
}
