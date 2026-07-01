package net.sidwallmart.generalapp.service;

import net.sidwallmart.generalapp.entity.User;
import net.sidwallmart.generalapp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @ParameterizedTest
    @ValueSource(strings = {
            "ram",
            "shyam",
            "vipul"
    })
    void testFindByUserName(String name) {

        User user = new User();
        user.setUserName(name);

        when(userRepository.findByUserName(name))
                .thenReturn(user);

        assertNotNull(userService.findByUserName(name));
    }
}