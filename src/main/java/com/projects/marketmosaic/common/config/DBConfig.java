package com.projects.marketmosaic.common.config;

import com.projects.marketmosaic.common.utils.CommonUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DBConfig {

    private final String url;
    private final String userName;
    private final String password;
    private final int maxPoolSize;

    @Autowired
    public DBConfig(final CommonZookeeperConfig commonZookeeperConfig) {
        Object dbConfig = commonZookeeperConfig.getConfigValueByKey("DB_CONFIG");
        Map<String, Object> dbConfigMap = CommonUtils.toMap(dbConfig);
        this.url = dbConfigMap.get("url").toString();
        this.userName = dbConfigMap.get("userName").toString();
        this.password = dbConfigMap.get("password").toString();
        this.maxPoolSize = Integer.parseInt(dbConfigMap.get("maxPoolSize").toString());
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(userName);
        config.setPassword(password);
        config.setMaximumPoolSize(maxPoolSize);
        return new HikariDataSource(config);
    }
}
