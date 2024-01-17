package com.example.tiwo.IntegrationTests.Services;

import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Entities.UserEntity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ListServiceIntegrationTests {

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

    SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.GERMAN);

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
    void createListTest() throws ParseException {
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));

        ListEntity createdList = listService.createList(list);

        assertNotNull(createdList);
        assertEquals(list.getName(), createdList.getName());
        assertEquals(list.getDate(), createdList.getDate());
    }



    @Test
    void deleteListTest() throws ParseException {
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);

        UserEntity createdUser = userService.createUser(user);
        ListEntity createdList = listService.createList(list);
        OrderEntity createdOrder = orderService.createOrder(order);

        listService.addOrderToList(createdOrder.getId(), createdList.getId());
        userService.addListToUser(createdList.getId(), createdUser.getUsername());

        Optional<ListEntity> gotList = listService.getList(createdList.getId());

        listService.deleteList(gotList.get().getId());

        Optional<UserEntity> gotUser = userService.getUser(createdUser.getUsername());

        if (gotUser.isPresent()){
            assertEquals(0, gotUser.get().getLists().size());
        }
        else throw(new NullPointerException());

    }

    @Test
    void addOrderToListTest() throws ParseException {
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);

        ListEntity createdList = listService.createList(list);
        OrderEntity createdOrder = orderService.createOrder(order);

        listService.addOrderToList(createdOrder.getId(), createdList.getId());

        Optional<ListEntity> gotList = listService.getList(createdList.getId());
        Optional<OrderEntity> gotOrder = orderService.getOrder(createdOrder.getId());

        assertEquals(gotList.get().getId(), gotOrder.get().getList().getId());
        assertEquals(gotOrder.get().getId(), gotList.get().getOrders().get(0).getId());
    }


    @Test
    void updateListTest() throws ParseException {
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));

        ListEntity createdList = listService.createList(list);

        ListEntity returnedList = listService.updateList(createdList.getId(), "testowaNowa", formatter.parse("26-02-2023"));

        assertEquals("testowaNowa", returnedList.getName());
        assertEquals(formatter.parse("26-02-2023"), returnedList.getDate());

    }
}
