package org.blockchain.borrowing.service;

import org.blockchain.borrowing.Application;
import org.blockchain.borrowing.domain.User;
import org.blockchain.borrowing.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
public class TradeServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    User user;

    @Before
    public void setup() {
        user = new User();
        user.setAmount(BigDecimal.valueOf(1000));

        userService.save(user);
        Assert.assertTrue(user.getAmount().equals(BigDecimal.valueOf(1000)));
    }

    @After
    public void clean() {
        userService.
    }

    @Test
    public void test_create_trade() {


        System.out.print(userService.findById(1L));
    }
}
