package net.sidwallmart.generalapp.repository;

import net.sidwallmart.generalapp.entity.ConfigGeneralAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigGeneralAppRepository
        extends MongoRepository <ConfigGeneralAppEntity, ObjectId> {
}
