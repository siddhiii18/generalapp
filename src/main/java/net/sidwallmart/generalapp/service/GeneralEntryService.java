package net.sidwallmart.generalapp.service;

import lombok.extern.slf4j.Slf4j;
import net.sidwallmart.generalapp.entity.GeneralEntry;
import net.sidwallmart.generalapp.entity.User;
import net.sidwallmart.generalapp.repository.GeneralEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GeneralEntryService {

    @Autowired
    private GeneralEntryRepository generalEntryRepository; // service calls repository

    @Autowired
    private UserService userService;
    @Transactional
    public void saveEntry(GeneralEntry generalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName); // user nikala
            generalEntry.setDate(LocalDateTime.now());
            GeneralEntry saved = generalEntryRepository.save(generalEntry);
            //Generalentry ko save karaya in local variable named as saved see above. fir user find kiya tha upar fir niche uske
            // generalentries me add kiya wo saved entries jo ki db me ja chuki hai toh reference create ho gaya and then
            user.getGeneralEntries().add(saved);
            //user.setUserName(null); // null exception ayega while debugging so @transactional annotation use karo to act eveyrthing as a 1 single operation

            // then fir jo user hai usko bhi save kara liya in db with its new general entries
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry.", e);
        }
    }

    public void saveEntry(GeneralEntry generalEntry) {
        generalEntryRepository.save(generalEntry);
    }

    public List<GeneralEntry> getAll() {
        List<GeneralEntry> all = generalEntryRepository.findAll();
        System.out.println(all);
        return all;
    }

    public Optional<GeneralEntry> findById(ObjectId id) { //optional is kind of box jisme data can be present or can not be present
        return generalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName); // user find karenge by username
            removed = user.getGeneralEntries().removeIf(x -> x.getId().equals(id)); // now jo db me user hai humara getgeneralentries usme hum removeif chalayenge
            // wo generalentries remove ho jayegi iss list se jab x.get id equal hogi humari id ke
            if (removed) {
                userService.saveUser(user); // updated user save hoga na ki new user , updated one hoga save
                generalEntryRepository.deleteById(id); //general entry delete ho jayegi.
            }
        } catch (Exception e) {
            log.error("Error ", e);
            throw new RuntimeException("An error occured while deleting the entry.", e);
        }
        return removed;
    }
}
