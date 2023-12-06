package com.rms.schedulers;

import com.rms.interceptors.NightModeInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NightModeScheduler {

    private final NightModeInterceptor nightModeInterceptor;

    public NightModeScheduler(NightModeInterceptor nightModeInterceptor) {
        this.nightModeInterceptor = nightModeInterceptor;
    }

    @Scheduled(cron = "0 0 20 * * *") // sec min hours day month dayWeek
    public void activateNightMode() {
        System.out.println("Scheduled NightMode Start");
        nightModeInterceptor.NightModeStart();
    }

    @Scheduled(cron = "0 0 7 * * *") // sec min hours day month dayWeek
    public void deactivateMaintenance() {
        System.out.println("Scheduled NightMode Stop");
        nightModeInterceptor.NightModeStop();
    }
}
