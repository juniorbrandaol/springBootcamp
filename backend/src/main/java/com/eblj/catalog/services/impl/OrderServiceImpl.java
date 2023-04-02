package com.eblj.catalog.services.impl;

import com.eblj.catalog.entities.*;
import com.eblj.catalog.entities.enuns.OrderStatus;
import com.eblj.catalog.repositories.OrderItemRepository;
import com.eblj.catalog.repositories.OrderRepository;
import com.eblj.catalog.repositories.ProductRepository;
import com.eblj.catalog.repositories.UserRepository;
import com.eblj.catalog.rest.DTO.OrderDTO;
import com.eblj.catalog.rest.DTO.OrderItemDTO;
import com.eblj.catalog.services.OrderService;
import com.eblj.catalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order save(OrderDTO dto) {

        Long idCliente = dto.getClient();
        User user = userRepository
            .findById(idCliente)
            .orElseThrow(()->new  ResourceNotFoundException("Usuário não encontrado "+idCliente ) );
        Order order = new Order();
      //  Payment pay = new Payment();
        order.setMoment(Instant.now());
        order.setOrderStatus(OrderStatus.RECEIVED);
        order.setClient(user);
        order.setOrderAmount(order.getTotal());
        List<OrderItem> items= toOrderItem(order,dto.getItems());
        repository.save(order);
        orderItemRepository.saveAll(items);
        order.setItems(items);
      //  pay.setMomnet(Instant.now());
       // pay.setOrder(order);
     //   pay.setTotalValue(order.getTotal());
     //   order.setPayment(pay);
        return order;
    }
    @Transactional(readOnly = true)
    @Override
    public List<OrderDTO> getAllOrder(Long id) {
        Order obj= repository.findById(id)
                .orElseThrow(()->new  ResourceNotFoundException("Pedido não encontrado: "+id ) );
        return repository.findByIdFetchOrder(id).stream()
        .map( p -> toOrderDTO(p) ).toList();
    }

    @Override
    @Transactional
    public void updateStatus(Long id, OrderStatus status) {

        repository.findById(id).map( order-> {
            order.setStatus(status);
            repository.save(order);
            return order;
        }).orElseThrow(()-> new ResourceNotFoundException("Pedido não enconrado"));

    }
    private List<OrderItem> toOrderItem(Order order, List<OrderItemDTO> items){

        if(items.isEmpty()){
            throw new ResourceNotFoundException("Lista de items vazia");
        }
        return items
                .stream()
                .map(dto ->{
                    Long idProduct = dto.getProduct();
                    Product product=  productRepository
                            .findById(idProduct)
                            .orElseThrow(()-> new ResourceNotFoundException("Produto não enconrado "+idProduct));
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(dto.getQuantity());
                    orderItem.setPrice(dto.getPrice());
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    return  orderItem;
                } ).collect(Collectors.toList());
    }
    private OrderDTO toOrderDTO(Order order){
        OrderDTO info = new OrderDTO();
        info.setId(order.getId());
        info.setMoment(order.getMoment());
        info.setCpf(order.getClient().getCpf());
        info.setCliente(order.getClient().getId());
        info.setOrderAmount(order.getTotal());
        info.setStatus(order.getStatus());
        info.setItems(toOrderItemDTO(order.getItems()));
        return  info;
    }
    public List<OrderItemDTO> toOrderItemDTO(List<OrderItem> items){
        OrderItemDTO info = new OrderItemDTO();
        if(CollectionUtils.isEmpty(items)){
            return  Collections.emptyList();
        }
        return  items.stream().map( item-> {
            info.setProduct(item.getProduct().getId());
            info.setPrice(item.getPrice());
            info.setQuantity(item.getQuantity());
            return info;
        }).collect(Collectors.toList());
    }



}
