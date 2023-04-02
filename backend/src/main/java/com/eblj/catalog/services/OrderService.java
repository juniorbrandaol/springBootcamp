package com.eblj.catalog.services;

import com.eblj.catalog.entities.Order;
import com.eblj.catalog.entities.enuns.OrderStatus;
import com.eblj.catalog.rest.DTO.OrderDTO;

import java.util.List;

public interface OrderService {

    Order save(OrderDTO dto);
    List<OrderDTO> getAllOrder(Long id);
    void updateStatus(Long id, OrderStatus status);
}
