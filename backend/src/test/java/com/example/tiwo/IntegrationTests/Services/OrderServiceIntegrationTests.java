package com.example.tiwo.IntegrationTests.Services;

import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Repositories.ItemRepository;
import com.example.tiwo.Repositories.ListRepository;
import com.example.tiwo.Repositories.OrderRepository;
import com.example.tiwo.Repositories.UserRepository;
import com.example.tiwo.Services.ItemService;
import com.example.tiwo.Services.ListService;
import com.example.tiwo.Services.OrderService;
import com.example.tiwo.Services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceIntegrationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListRepository listRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @Autowired
    ListService listService;


    @BeforeEach
    void beforeEach(){

    }

    @AfterEach
    void afterEach(){
        userRepository.deleteAll();
        listRepository.deleteAll();
        orderRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    void createOrderTest(){
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);

        OrderEntity createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(order.getAmount(), createdOrder.getAmount());
        assertEquals(order.getGrammage(), createdOrder.getGrammage());


    }


    @Test
    void toggleRealizedTest(){
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);

        OrderEntity createdOrder = orderService.createOrder(order);

        orderService.toggleRealized(createdOrder.getId());

        Optional<OrderEntity> gotOrder = orderService.getOrder(createdOrder.getId());

        assertTrue(gotOrder.get().isRealized());
    }

    @Test
    void addItemTest(){
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);
        ItemEntity item = new ItemEntity(null, "DrukarkaNowiutka", "się psuje");

        OrderEntity createdOrder = orderService.createOrder(order);
        ItemEntity createdItem = itemService.createItem(item);

        orderService.addItem(createdItem.getName(), createdOrder.getId());

        Optional<OrderEntity> gotOrder = orderService.getOrder(createdOrder.getId());

        assertEquals(createdItem.getId(), gotOrder.get().getItem().getId());

    }



}
