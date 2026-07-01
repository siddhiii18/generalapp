package net.sidwallmart.generalapp.service;

import lombok.extern.slf4j.Slf4j;
import net.sidwallmart.generalapp.controller.GeneralEntryControllerV2;
import net.sidwallmart.generalapp.entity.User;
import net.sidwallmart.generalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository; // service calls repository

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("hahaha");
            log.warn("hahaha");
            log.info("hahaha");
            log.debug("hahaha");
            log.trace("hahaha");
            return false;
        }
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public  Optional<User> findById(ObjectId id){ //optional is kind of box jisme data can be present or cannot be present
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){

        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
