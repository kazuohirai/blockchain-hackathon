package org.blockchain.borrowing.service;

import org.apache.log4j.Logger;
import org.blockchain.borrowing.domain.Trade;
import org.blockchain.borrowing.domain.User;
import org.blockchain.borrowing.repository.TradeRepository;
import org.blockchain.borrowing.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TradeService {

    private static final Logger LOG = Logger.getLogger(TradeService.class);

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private UserService userService;

    public Trade findOne(String tranNo) {
        return tradeRepository.findOne(tranNo);
    }

    public List<Trade> listByBorrowerAndStatues(long userId, Collection<Trade.Status> statues) {
        return tradeRepository.findByBorrowerAndStatusIn(userId, statues);
    }

    public List<Trade> listByLenderAndStatues(long userId, Collection<Trade.Status> statues) {
        return tradeRepository.findByLenderAndStatusIn(userId, statues);
    }


    public Trade postTrade(Trade trade) {
        ValidateUtils.notNulls(trade.getBorrower(), trade.getAmount(), trade.getInterest());

        trade.setTradeNo(UUID.randomUUID().toString());
        trade.setCreateTime(new Date());

        trade = tradeRepository.save(trade);
        return trade;
    }


    public Trade borrowTrade(long userId, Trade trade) {
        LOG.info(String.format("borrow trade of trade %s, user id %s", trade, userId));

        User currentUser = userService.findById(userId);
        userService.deduct(currentUser, trade.getAmount());

        trade.setStatus(Trade.Status.ING);
        trade = tradeRepository.save(trade);

        return trade;
    }


    public Trade repayTrade(Trade trade) {
        LOG.info(String.format("repay trade of %s", trade));

        User borrowUser = userService.findById(trade.getBorrower());
        User lenderUser = userService.findById(trade.getLender());
        Double money = trade.getAmount();

        userService.recharge(borrowUser, money);
        userService.deduct(lenderUser, money);

        trade.setStatus(Trade.Status.COM);
        trade = tradeRepository.save(trade);

        return trade;
    }
}
