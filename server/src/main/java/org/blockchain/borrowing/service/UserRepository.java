package org.blockchain.borrowing.service;

import org.blockchain.borrowing.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * Entity Dao
 *
 * Created by pengchangguo on 16/1/9.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}