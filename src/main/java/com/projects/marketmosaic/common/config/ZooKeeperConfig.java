package com.projects.marketmosaic.common.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@Configuration
@RefreshScope
@Slf4j
@NoArgsConstructor
public class ZooKeeperConfig {

    @Value("${config-properties:default-value}")
    private String configValue;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, Object> configMap = Collections.emptyMap();

    private static ZooKeeperConfig instance;

    @PostConstruct
    public void init() {
        initStaticInstance(this);
        loadDecodedConfig();
    }

    private static void initStaticInstance(ZooKeeperConfig zooKeeperConfig) {
        instance = zooKeeperConfig;
    }

    /**
     * Loads and decodes the Base64-encoded ZooKeeper config value
     * and populates the config map.
     */
    private void loadDecodedConfig() {
        try {
            if (configValue == null || configValue.equals("default-value")) {
                log.warn("ZooKeeper config is not set or uses default value");
                return;
            }

            String decoded = new String(Base64.getDecoder().decode(configValue));
            configMap = objectMapper.readValue(decoded, new TypeReference<>() {});

        } catch (Exception e) {
            log.error("Failed to decode or parse ZooKeeper config: {}", e.getMessage(), e);
        }
    }

    public String getStringValueByKey(String key) {
        Object value = getConfigValueByKey(key);
        return value != null ? value.toString() : null;
    }

    public Object getConfigValueByKey(String key) {
        if (configMap == null || configMap.isEmpty()) {
            log.warn("ZooKeeper config map is empty");
            return null;
        }
        return configMap.get(key);
    }

    /**
     * Call this method manually if you're using programmatic refresh outside /actuator/refresh
     */
    public static void refreshData() {
        log.info("Manual ZooKeeper config refresh triggered");
        instance.loadDecodedConfig();
    }
}
