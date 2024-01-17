package com.example.tiwo.Services;

import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Exceptions.NoSuchItemException;
import com.example.tiwo.Exceptions.NoSuchOrderException;
import com.example.tiwo.Repositories.ItemRepository;
import com.example.tiwo.Repositories.OrderRepository;
import com.example.tiwo.Repositories.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    OrderRepository orderRepository;
    ItemRepository itemRepository;
    ListRepository listRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, ListRepository listRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.listRepository = listRepository;
    }

    public OrderEntity createOrder(OrderEntity orderEntity){
        return orderRepository.save(orderEntity);
    }

    /*
    public void deleteOrder(OrderEntity orderEntity){
        orderRepository.delete(orderEntity);
    }
    */
    public Optional<OrderEntity> getOrder(Long id){
        return orderRepository.findById(id);
    }


    public OrderEntity toggleRealized(Long id){
        Optional<OrderEntity> order = orderRepository.findById(id);
        if (order.isPresent()){
            order.get().setRealized(!order.get().isRealized());
            return orderRepository.save(order.get());
        }
        else {
            throw new NoSuchOrderException();
        }
    }

    public void addItem(String itemName, Long orderId){
        Optional<ItemEntity> item = itemRepository.getByName(itemName);
        Optional<OrderEntity> order = orderRepository.findById(orderId);

        if (item.isPresent()){
            if (order.isPresent()) {
                order.get().setItem(item.get());

                orderRepository.save(order.get());
            }
            else {
                throw new NoSuchOrderException();
            }
        }
        else {
            throw new NoSuchItemException();
        }
    }

    /*
    public OrderEntity updateOrder(Long id, OrderEntity orderEntity){
        Optional<OrderEntity> order = orderRepository.findById(id);
        if(order.isPresent()){
            OrderEntity oe = order.get();
            oe.setId(orderEntity.getId());
            oe.setAmount(orderEntity.getAmount());
            oe.setGrammage(orderEntity.getGrammage());

            return orderRepository.save(oe);
        }
        else{
            throw(new NullPointerException());
        }
    }
    */
}