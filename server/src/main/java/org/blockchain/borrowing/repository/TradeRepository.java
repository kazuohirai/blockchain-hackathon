package org.blockchain.borrowing.repository;

import org.blockchain.borrowing.domain.Trade;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by yann on 1/9/16.
 */
public interface TradeRepository extends CrudRepository<Trade, String> {

    List<Trade> findByBorrowerAndStatusIn(long borrower, Collection<Trade.Status> statues);

    List<Trade> findByLenderAndStatusIn(long lender, Collection<Trade.Status> statues);

    List<Trade> findByBorrowerInAndStatusIn(Collection<Long> friends, Collection<Trade.Status> statues);
}
