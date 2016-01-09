package org.blockchain.borrowing.web;

import org.apache.log4j.Logger;
import org.blockchain.borrowing.domain.User;
import org.blockchain.borrowing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User Controller
 * <p>
 * Created by pengchangguo on 16/1/9.
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class);

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

    @RequestMapping(method = RequestMethod.POST)
    public User register(@RequestBody User userInfo) {
        return new User();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User loginInfo) {
        return new User();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "id") Long id) {
        return new User();
    }
}
