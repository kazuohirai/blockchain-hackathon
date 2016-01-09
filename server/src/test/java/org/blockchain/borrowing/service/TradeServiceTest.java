package org.blockchain.borrowing.service;

import org.blockchain.borrowing.Application;
import org.blockchain.borrowing.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
public class TradeServiceTest {

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        User user = new User();
    }

    @Test
    public void test_create_trade() {

        System.out.print(userService.findById(1L));
    }
}
