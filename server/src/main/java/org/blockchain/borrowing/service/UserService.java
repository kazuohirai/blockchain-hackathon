package org.blockchain.borrowing.service;

import org.blockchain.borrowing.domain.User;
import org.blockchain.borrowing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User register(){
        return new User();
    }



}
