package net.sidwallmart.generalapp.controller;
import net.sidwallmart.generalapp.api.response.WeatherResponse;
import net.sidwallmart.generalapp.entity.User;
import net.sidwallmart.generalapp.repository.UserRepository;
import net.sidwallmart.generalapp.service.UserService;
import net.sidwallmart.generalapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        // requestbody help se update karenge not by id.
        // requestbody - isme username and pass ayega toh hum username isse find out karenge kis user ka hai details and all

        User userIndb = userService.findByUserName(userName);
        userIndb.setUserName(user.getUserName());
        userIndb.setPassword(user.getPassword());
            // it means requestbody me naye ya purane naye ya purane username pass aa rahe hai wo set kara diyaaa.
            //postman se username pass aya hoga toh db me jo mila hai usme naya username and password we r setting.
        userService.saveNewUser(userIndb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            // isse same id pe save ho jayega
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");

        String greeting = "";

        if (weatherResponse != null &&
                weatherResponse.getCurrent() != null) {

            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }

        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }
}