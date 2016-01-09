package org.blockchain.borrowing.repository;

import org.blockchain.borrowing.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * Entity Dao
 *
 * Created by pengchangguo on 16/1/9.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByPhone(String phone);
}