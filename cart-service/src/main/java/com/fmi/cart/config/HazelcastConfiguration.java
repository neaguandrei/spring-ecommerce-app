package com.fmi.cart.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config configuration() {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance");
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("configuration");
        mapConfig.setTimeToLiveSeconds(-1);
        config.addMapConfig(mapConfig);

        return config;
    }

}
