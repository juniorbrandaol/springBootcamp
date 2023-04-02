package com.eblj.catalog.rest.resources;

import com.eblj.catalog.entities.Order;
import com.eblj.catalog.entities.enuns.OrderStatus;
import com.eblj.catalog.rest.DTO.*;
import com.eblj.catalog.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping(value="/api/orders")
public class OrderResource {
    @Autowired
    private OrderService service;
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long save(@Valid @RequestBody OrderDTO dto) {
        Order order = service.save(dto);
         return order.getId();
    }
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus( @PathVariable Long id, @RequestBody UpdateStatusOrderDTO dto){
        String newStatus = dto.getNewStatus();
        service.updateStatus(id, OrderStatus.valueOf(newStatus));
    }
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public List<OrderDTO> getById(@PathVariable  Long id){
        return service.getAllOrder(id);
    }

}
