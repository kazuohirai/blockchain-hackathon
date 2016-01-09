package org.blockchain.borrowing.web;

import org.apache.log4j.Logger;
import org.blockchain.borrowing.domain.Trade;
import org.blockchain.borrowing.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
    @RequestMapping(method = RequestMethod.GET)
    public List<Trade> list(@PathVariable("userId") long userId,
                            @RequestParam(value = "status", required = false, defaultValue = "ING") String status) { /* INIT 申请的借款,  ING 借出去的和借别人的, COM 完成的借款 */

        Trade.Status tradeStatus = Trade.Status.valueOf(status);
        List<Trade> trades;
        if (tradeStatus.equals(Trade.Status.INIT) || tradeStatus.equals(Trade.Status.COM)) {
            trades = tradeService.listByBorrowerAndStatues(userId, Arrays.asList(tradeStatus));
        } else if (tradeStatus.equals(Trade.Status.ING)) {
            trades = tradeService.listByBorrowerAndStatues(userId, Arrays.asList(tradeStatus));
            trades.addAll(tradeService.listByLenderAndStatues(userId, Arrays.asList(tradeStatus)));
        }

        return Arrays.asList(Trade.sampleValue(), Trade.sampleValue(), Trade.sampleValue());
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

        trade = tradeService.postTrade(trade);

        return Trade.sampleValue();
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

        return Trade.sampleValue();
    }


    @RequestMapping(path = "/{tradeNo}", method = RequestMethod.POST)
    public Trade update(@PathVariable("userId") long userId,
                        @PathVariable("tradeNo") String tradeNo,
                        @RequestParam(value = "status", required = false, defaultValue = "ING") String status) { /* ING 借钱, COM 还钱 */

        Trade.Status tradeStatus = Trade.Status.valueOf(status);
        Trade trade = tradeService.findOne(tradeNo);

        if (tradeStatus.equals(Trade.Status.ING)) {
            trade = tradeService.borrowTrade(userId, trade);
        } else {
            trade = tradeService.repayTrade(trade);
        }

        return Trade.sampleValue();
    }
}
