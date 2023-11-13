package com.rms.service;

import com.rms.model.entity.TableEntity;
import com.rms.model.entity.UserEntity;
import com.rms.repositiry.TableRepository;
import org.springframework.stereotype.Service;

@Service
public class TableService {

    private final TableRepository tableRepository;
    private final UserService userService;


    public TableService(TableRepository tableRepository, UserService userService) {
        this.tableRepository = tableRepository;
        this.userService = userService;
    }

    public void addTables() {

        if (tableRepository.count() != 0) {
            return;
        }

        UserEntity waiter1 = userService.findUserEntityByUsername("waiter1");
        UserEntity waiter2 = userService.findUserEntityByUsername("waiter2");

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                addTable(waiter1);
            } else {
                addTable(waiter2);
            }
        }

    }

    private void addTable(UserEntity waiter) {
        TableEntity table = new TableEntity();
        table.setCapacity(10);
        table.isReserved(false);
        table.setWaiter(waiter);
        tableRepository.save(table);
    }

}
