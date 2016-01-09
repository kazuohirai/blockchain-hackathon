package org.blockchain.borrowing.service;

import org.blockchain.borrowing.BorrowException;
import org.blockchain.borrowing.domain.User;
import org.blockchain.borrowing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @param user
     * @return
     */
    public User registry(User user) {
        return userRepository.save(user);
    }

    public User logon(String phone, String password) {
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new BorrowException("没有该用户");
        }
        if (!password.equalsIgnoreCase(user.getPassword())) {
            throw new BorrowException("密码不正确");
        }
        return user;
    }

    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    public User deduct(User user, Double amount) {
        return User.sampleValue();
    }

    public User recharge(User user, Double amount) {
        return User.sampleValue();
    }
}
