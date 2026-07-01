package net.sidwallmart.generalapp.cron;

import net.sidwallmart.generalapp.scheduler.UserScheduler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserSchedulersTest {

    @Autowired
    private UserScheduler userScheduler;

    @Test
    void testFetchUsersAndSendSaMail() {
        userScheduler.fetchUsersAndSendSaMail();
    }
}