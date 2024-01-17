package com.example.tiwo.Services;

import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Entities.UserEntity;
import com.example.tiwo.Exceptions.NoSuchListException;
import com.example.tiwo.Exceptions.NoSuchOrderException;
import com.example.tiwo.Repositories.ListRepository;
import com.example.tiwo.Repositories.OrderRepository;
import com.example.tiwo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ListService {
    ListRepository listRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;

    @Autowired
    public ListService(ListRepository listRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.listRepository = listRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public ListEntity createList(ListEntity listEntity){
        return listRepository.save(listEntity);
    }

    /*
    public void deleteList(ListEntity listEntity){
        listRepository.delete(listEntity);
    }
    */
    public void deleteList(Long id){
        ListEntity list = listRepository.getReferenceById(id);
        List<OrderEntity> orders = new ArrayList<OrderEntity>(list.getOrders());

        //list.getOrders().clear();

        orderRepository.deleteAll(orders);

        list.getUser().getLists().remove(list);
        listRepository.delete(list);
    }


    public Optional<ListEntity> getList(Long id){
        return listRepository.findById(id);
    }

    public void addOrderToList(Long orderId, Long listId){
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        Optional<ListEntity> list = listRepository.findById(listId);
        if (list.isPresent()){
            if (order.isPresent()) {
                order.get().setList(list.get());
                list.get().getOrders().add(order.get());

                orderRepository.save(order.get());
                listRepository.save(list.get());
            }
            else {
                throw new NoSuchOrderException();
            }
        }
        else {
            throw new NoSuchListException();

        }
    }

    /*
    public void addUserToList(String username, Long listId){
        Optional<UserEntity> user = userRepository.getByUsername(username);
        Optional<ListEntity> list = listRepository.findById(listId);
        if(list.isPresent()) {
            //list.get().setUser(user.get());

            listRepository.save(list.get());
        }
        else{
            throw(new NullPointerException());
        }
    }

     */
    public ListEntity updateList(Long id, String name, Date date){
        Optional<ListEntity> list = listRepository.findById(id);
        if(list.isPresent()){
            ListEntity le = list.get();
            //le.setUser(listEntity.getUser());
            le.setName(name);
            le.setDate(date);

            return listRepository.save(le);
        }
        else{
            throw(new NoSuchListException());
        }
    }

}