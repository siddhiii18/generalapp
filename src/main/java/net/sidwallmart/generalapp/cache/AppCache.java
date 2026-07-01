package net.sidwallmart.generalapp.cache;

import jakarta.annotation.PostConstruct;
import net.sidwallmart.generalapp.entity.ConfigGeneralAppEntity;
import net.sidwallmart.generalapp.repository.ConfigGeneralAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }

    @Autowired
    private ConfigGeneralAppRepository configGeneralAppRepository;

    public Map<String, String> appCache;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<ConfigGeneralAppEntity> all = configGeneralAppRepository.findAll();

        for (ConfigGeneralAppEntity configGeneralAppEntity : all) {
            appCache.put(
                    configGeneralAppEntity.getKey(),
                    configGeneralAppEntity.getValue()
            );
        }
    }
}