package com.rms.schedulers;

import com.rms.interceptors.MaintenanceInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class MaintenanceScheduler {

    private final MaintenanceInterceptor maintenanceInterceptor;

    public MaintenanceScheduler(MaintenanceInterceptor maintenanceInterceptor) {
        this.maintenanceInterceptor = maintenanceInterceptor;
    }

    @Scheduled(cron = "0 0 2 * * *") // sec min hours day month dayWeek
    public void activateMaintenance() {
        System.out.println("Scheduled Maintenance Start");
        maintenanceInterceptor.MaintenanceModeStart();
    }

    @Scheduled(cron = "0 0 3 * * *") // sec min hours day month dayWeek
    public void deactivateMaintenance() {
        System.out.println("Scheduled Maintenance Stop");
        maintenanceInterceptor.MaintenanceModeStop();
    }
}