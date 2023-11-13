package com.rms.service;

import com.rms.model.entity.OrderEntity;
import com.rms.model.entity.TableEntity;
import com.rms.repositiry.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TableService tableService;

    public OrderService(OrderRepository orderRepository, TableService tableService) {
        this.orderRepository = orderRepository;
        this.tableService = tableService;
    }

    public void initMenu() {

        OrderEntity menu = new OrderEntity();

        TableEntity table = tableService.findById(1L); //taking 1 table

        if (table != null) {
            menu.setCompleted(true);
            menu.setTable(table);
            menu.setPaid(true);
            menu.setDateTime(LocalDateTime.now());
            orderRepository.save(menu);
        }
    }

    public OrderEntity findById (Long id) {
        return orderRepository.findOrderEntitiesById(id).orElse(null);
    }
}
