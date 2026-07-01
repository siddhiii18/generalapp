package net.sidwallmart.generalapp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("sidghode27@gmail.com",
                "Testing java mail sender",
                "Hi Siddhi, How are you ?");
    }
}
