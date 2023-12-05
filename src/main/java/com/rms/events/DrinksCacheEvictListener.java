package com.rms.events;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DrinksCacheEvictListener implements ApplicationListener<DrinksCacheEvictEvent> {

    @Override
    @CacheEvict(value = "drinksCache", allEntries = true)
    public void onApplicationEvent(DrinksCacheEvictEvent event) {

    }
}