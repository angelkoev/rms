package com.rms.events;

import org.springframework.context.ApplicationEvent;

public class DrinksCacheEvictEvent extends ApplicationEvent {

    public DrinksCacheEvictEvent(Object source) {
        super(source);
    }
}
