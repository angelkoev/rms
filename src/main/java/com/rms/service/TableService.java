//package com.rms.service;
//
//import com.rms.model.entity.TableEntity;
//import com.rms.model.entity.UserEntity;
//import com.rms.repositiry.TableRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class TableService {
//
//    private final TableRepository tableRepository;
//    private final UserService userService;
//
//
//    public TableService(TableRepository tableRepository, UserService userService) {
//        this.tableRepository = tableRepository;
//        this.userService = userService;
//    }
//
//    public TableEntity findById(Long id) {
//        return this.tableRepository.findTableEntitiesById(id).orElse(null);
//    }
//
//    public void initTables() {
//
//        if (tableRepository.count() != 0) {
//            return;
//        }
//
//        UserEntity waiter1 = userService.findUserEntityByUsername("waiter1");
//        UserEntity waiter2 = userService.findUserEntityByUsername("waiter2");
////        UserEntity waiter1 = new UserEntity();
////        UserEntity waiter2 = new UserEntity();
//
//        for (int i = 0; i < 10; i++) {
//            if (i % 2 == 0) {
//                initTable(waiter1);
//            } else {
//                initTable(waiter2);
//            }
//        }
//
//    }
//
//    private void initTable(UserEntity waiter) {
//        TableEntity table = new TableEntity();
//        table.setCapacity(10);
//        table.isReserved(false);
//        table.setWaiter(waiter);
//        tableRepository.save(table);
//    }
//
//}
