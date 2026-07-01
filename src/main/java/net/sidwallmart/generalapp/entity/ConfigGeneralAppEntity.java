package net.sidwallmart.generalapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_general_app")
@Data
@NoArgsConstructor
public class ConfigGeneralAppEntity {

    @Id
    private ObjectId id;

    public String key;
    public String value;
}