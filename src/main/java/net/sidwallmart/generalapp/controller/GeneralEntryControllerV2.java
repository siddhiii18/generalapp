package net.sidwallmart.generalapp.controller;


import net.sidwallmart.generalapp.entity.GeneralEntry;
import net.sidwallmart.generalapp.entity.User;
import net.sidwallmart.generalapp.service.GeneralEntryService;
import net.sidwallmart.generalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/new")
public class GeneralEntryControllerV2 {

    @Autowired
    private GeneralEntryService generalEntryService; // it simply means GeneralEntryService ka ek instance bana liya hoga spring ne and wo humne yaha pe (GeneralEntryControllerV2) iss class me inject kar diya
    // AND udhar usko service me @component bana diya so that it wont be null, so spring jo hai GEservice ka object bana lega apne pass
    // and autowired kiya hai yaha toh jisko chahiye it can use it as object

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllGeneralEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<GeneralEntry> all = user.getGeneralEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<GeneralEntry> createEntry(@RequestBody GeneralEntry myEntry) { //<Type>
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            myEntry.setDate(LocalDateTime.now());
            System.out.println("POST HIT");
            generalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "id/{myId}")
    public ResponseEntity<GeneralEntry> getGeneralEntryById(@PathVariable("myId") ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<GeneralEntry> collect = user.getGeneralEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<GeneralEntry> generalEntry = generalEntryService.findById(myId);
            if (generalEntry.isPresent()) {
                return new ResponseEntity<>(generalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // by id find kiya and nahi mila toh response 404 not found ayega

    }

    @DeleteMapping(path = "id/{myId}")
    public ResponseEntity<?> deleteGeneralEntryById(@PathVariable ObjectId myId) {
        // "?" IS A WILD CARD PATTERN IT IS NOT NECESSARY KI VAHA PE<> ENTITY CLASS DENI HI PADE.
        // KISI aur class ka obj yaha pe response entity ke andar wrap karke return kara sakte hai..ResponseEntity<>
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = generalEntryService.deleteById(myId, userName);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{myId}") //update
    public ResponseEntity<?> updateGeneralById(
            @PathVariable ObjectId myId,
            @RequestBody GeneralEntry newEntry
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<GeneralEntry> collect = user.getGeneralEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<GeneralEntry> generalEntry = generalEntryService.findById(myId);
            if (generalEntry.isPresent()) {
                GeneralEntry old = generalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                generalEntryService.saveEntry(old); // old vale ko save kara lo and return it
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // by id find kiya and nahi mila toh response 404 not found ayega
    }
}