package com.rms.events;

import org.springframework.context.ApplicationEvent;

public class FoodsCacheEvictEvent extends ApplicationEvent {

    public FoodsCacheEvictEvent(Object source) {
        super(source);
    }
}

