package org.blockchain.borrowing.web;

import org.blockchain.borrowing.domain.User;
import org.blockchain.borrowing.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User Controller
 *
 * Created by pengchangguo on 16/1/9.
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> index() {
        User liming = new User();
        liming.setName("liming");
        User pcg = new User();
        pcg.setName("pcg");
        userRepository.save(liming);
        userRepository.save(pcg);
        return userRepository.findAll();
    }
}
