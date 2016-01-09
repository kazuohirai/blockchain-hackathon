package org.blockchain.borrowing.web;

import org.apache.log4j.Logger;
import org.blockchain.borrowing.domain.User;
import org.blockchain.borrowing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    private UserService userService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public User registry(@RequestBody User user) {
        return userService.registry(user);
    }

    @RequestMapping(path = "/logon", method = RequestMethod.POST)
    public User logon(@RequestBody Map<String, String> map) {
        //return User.sampleValue();
        return userService.logon(map.get("phone"), map.get("password"));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public User get(@PathVariable(value = "id") Long id) {
        return User.sampleValue();
        //return userService.findById(id);
    }
}
