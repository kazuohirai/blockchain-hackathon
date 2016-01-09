package org.blockchain.borrowing.web;

import org.apache.log4j.Logger;
import org.blockchain.borrowing.domain.Trade;
import org.blockchain.borrowing.service.TradeService;
import org.blockchain.borrowing.web.vo.ValueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/{userId}/trade")
public class TradeController {

    private static final Logger LOG = Logger.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;

    /**
     * query all trades
     *
     * @param userId
     * @return
     */
    @RequestMapping(path = "/as-borrower", method = RequestMethod.GET)
    public ValueVo listAsBorrower(@PathVariable("userId") long userId,
                                  @RequestParam(value = "status", required = false, defaultValue = "ING") String status) { /* INIT 申请的借款,  ING 借出去的和借别人的, COM 完成的借款 */

        Trade.Status tradeStatus = Trade.Status.valueOf(status);
        List<Trade> trades = tradeService.listByBorrowerAndStatues(userId, Collections.singleton(tradeStatus));
        return ValueVo.aValue(trades);
    }

    @RequestMapping(path = "/as-lender", method = RequestMethod.GET)
    public ValueVo listAsLender(@PathVariable("userId") long userId,
                                @RequestParam(value = "status", required = false, defaultValue = "ING") String status) { /* INIT 申请的借款,  ING 借出去的和借别人的, COM 完成的借款 */

        Trade.Status tradeStatus = Trade.Status.valueOf(status);
        List<Trade> trades;
        if (tradeStatus.equals(Trade.Status.INIT)) {
            trades = tradeService.listByFriends(userId);
        } else {
            trades = tradeService.listByLenderAndStatues(userId, Collections.singleton(tradeStatus));
        }

        return ValueVo.aValue(trades);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ValueVo listAll(@PathVariable("userId") long userId,
                           @RequestParam(value = "status", required = false, defaultValue = "COM") String status) { /* INIT 申请的借款,  ING 借出去的和借别人的, COM 完成的借款 */

        Trade.Status tradeStatus = Trade.Status.valueOf(status);
        List<Trade> trades = new ArrayList<>();
        List<Trade> tradesAsBorrow = tradeService.listByBorrowerAndStatues(userId, Collections.singleton(tradeStatus));
        List<Trade> tradesAsLender = tradeService.listByLenderAndStatues(userId, Collections.singleton(tradeStatus));
        if (!tradesAsBorrow.isEmpty()) {
            trades.addAll(tradesAsBorrow);
        }

        if (!tradesAsLender.isEmpty()) {
            trades.addAll(tradesAsLender);
        }
        return ValueVo.aValue(trades);
    }


    /**
     * Create a trade
     *
     * @param userId
     * @param trade
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Trade create(@PathVariable("userId") long userId, @RequestBody Trade trade) {
        trade.setBorrower(userId);
        trade = tradeService.postTrade(trade);

        return trade;
    }

    /**
     * Get a detail of trade
     *
     * @param userId
     * @param tradeNo
     * @return
     */
    @RequestMapping(path = "/{tradeNo}", method = RequestMethod.GET)
    public Trade get(@PathVariable("userId") long userId, @PathVariable("tradeNo") String tradeNo) {

        Trade trade = tradeService.findOne(tradeNo);

        return trade;
    }

    /**
     * Repayment of borrow
     *
     * @param userId
     * @param tradeNo
     * @param status
     * @return
     */
    @RequestMapping(path = "/{tradeNo}", method = RequestMethod.POST)
    public Trade update(@PathVariable("userId") long userId,
                        @PathVariable("tradeNo") String tradeNo,
                        @RequestBody Map<String, String> status) { /* ING 借钱, COM 还钱 */

        Trade.Status tradeStatus = Trade.Status.valueOf(status.get("status"));
        Trade trade = tradeService.findOne(tradeNo);

        if (tradeStatus.equals(Trade.Status.ING)) {
            trade = tradeService.borrowTrade(userId, trade);
        } else {
            trade = tradeService.repayTrade(userId, trade);
        }

        return trade;
    }
}
