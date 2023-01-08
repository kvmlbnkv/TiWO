package com.example.tiwo.Services;

import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderEntity createOrder(OrderEntity orderEntity){
        return orderRepository.save(orderEntity);
    }

    public void deleteList(OrderEntity orderEntity){
        orderRepository.delete(orderEntity);
    }

    public Optional<OrderEntity> getOrder(Long id){
        return orderRepository.findById(id);
    }

    public OrderEntity updateOrder(Long id, OrderEntity orderEntity){
        Optional<OrderEntity> order = orderRepository.findById(id);
        if(order.isPresent()){
            OrderEntity oe = order.get();
            oe.setId(orderEntity.getId());
            oe.setListId(orderEntity.getListId());
            oe.setItemId(orderEntity.getItemId());
            oe.setAmount(orderEntity.getAmount());
            oe.setGrammage(orderEntity.getGrammage());

            return orderRepository.save(oe);
        }
        else{
            throw(new NullPointerException());
        }
    }

}