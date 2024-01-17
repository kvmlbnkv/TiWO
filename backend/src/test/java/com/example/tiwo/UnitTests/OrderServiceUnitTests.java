package com.example.tiwo.UnitTests;

import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Entities.UserEntity;
import com.example.tiwo.Exceptions.NoSuchItemException;
import com.example.tiwo.Exceptions.NoSuchOrderException;
import com.example.tiwo.Repositories.ItemRepository;
import com.example.tiwo.Repositories.ListRepository;
import com.example.tiwo.Repositories.OrderRepository;
import com.example.tiwo.Services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OrderServiceUnitTests {

    @Mock
    OrderRepository orderRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    ListRepository listRepository;

    @InjectMocks
    OrderService orderService;


    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrderTest(){
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);

        when(orderRepository.save(order)).thenReturn(order);

        OrderEntity createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(order.getId(), createdOrder.getId());
        assertEquals(order.getList(), createdOrder.getList());
        assertEquals(order.getItem(), createdOrder.getItem());
        assertEquals(order.getAmount(), createdOrder.getAmount());
        assertEquals(order.getGrammage(), createdOrder.getGrammage());


    }

    @Test
    void toggleRealizedTest(){
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        orderService.toggleRealized(order.getId());

        assertTrue(order.isRealized());
    }

    @Test
    void toggleRealizedOrderExceptionTest(){
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchOrderException.class, ()->orderService.toggleRealized(order.getId()));
    }


    @Test
    void addItemTest(){
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);
        ItemEntity item = new ItemEntity(null, "Drukarka", "się psuje");

        when(itemRepository.getByName(item.getName())).thenReturn(Optional.of(item));
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        orderService.addItem(item.getName(), order.getId());

        assertEquals(item, order.getItem());

    }

    @Test
    void addItemOrderExceptionTest(){
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);
        ItemEntity item = new ItemEntity(null, "Drukarka", "się psuje");

        when(itemRepository.getByName(item.getName())).thenReturn(Optional.of(item));
        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchOrderException.class, ()->orderService.addItem(item.getName(), order.getId()));

    }

    @Test
    void addItemItemExceptionTest(){
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);
        ItemEntity item = new ItemEntity(null, "Drukarka", "się psuje");

        when(itemRepository.getByName(item.getName())).thenReturn(Optional.empty());
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        assertThrows(NoSuchItemException.class, ()->orderService.addItem(item.getName(), order.getId()));

    }
}
