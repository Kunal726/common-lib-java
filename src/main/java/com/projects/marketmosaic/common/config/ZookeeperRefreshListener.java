package com.projects.marketmosaic.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnMissingBean(name = "zookeeperRefreshListener")
public class ZookeeperRefreshListener implements ApplicationListener<EnvironmentChangeEvent> {
    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        if (event.getKeys().contains("config-properties")) {
            ZooKeeperConfig.refreshData();
        } else if (event.getKeys().contains("marketmosaic-common")) {
            CommonZookeeperConfig.refreshData();
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }


    @EventListener(RefreshScopeRefreshedEvent.class)
    public void onRefresh(RefreshScopeRefreshedEvent event) {
        ZooKeeperConfig.refreshData();
        CommonZookeeperConfig.refreshData();
    }
}
