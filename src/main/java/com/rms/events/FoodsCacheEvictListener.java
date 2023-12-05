package com.rms.events;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class FoodsCacheEvictListener implements ApplicationListener<FoodsCacheEvictEvent> {

    @Override
    @CacheEvict(value = "foodsCache", allEntries = true)
    public void onApplicationEvent(FoodsCacheEvictEvent event) {

    }
}

