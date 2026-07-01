package net.sidwallmart.generalapp.repository;

import net.sidwallmart.generalapp.entity.GeneralEntry;
import net.sidwallmart.generalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> { // 2 parameters <GE, ID>
// interface banaya usko extends kiya mongorepository jisme bhaut methods hai findall,getall,saveall, sare documents insert delete krna ho from collection
    //mongorepo is a interface pehle se hi diya hai by spring data mongodb jo humne pom.xml dependency me add kiya hai
    // and its like crud operations also
    //create update delete

    User findByUserName(String username);

    User deleteByUserName(String username);
}
