package com.example.tiwo.IntegrationTests.Services;

import com.example.tiwo.Entities.ItemEntity;
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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ItemServiceIntegrationTests {

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
    void createItemTest(){
        ItemEntity item = new ItemEntity(null, "Drukarkanowiutka", "się psuje");

        ItemEntity createdItem = itemService.createItem(item);

        assertNotNull(createdItem);
        assertEquals(item.getName(), createdItem.getName());
        assertEquals(item.getDescription(), createdItem.getDescription());
    }

    @Test
    void getItemTest(){
        ItemEntity item = new ItemEntity(null, "Drukarkanowiutkasztuka", "się psuje");

        ItemEntity createdItem = itemService.createItem(item);

        Optional<ItemEntity> gotItem = itemService.getItem(createdItem.getName());

        if (gotItem.isPresent()){
            assertEquals(createdItem.getId(), gotItem.get().getId());
            assertEquals(createdItem.getName(), gotItem.get().getName());
            assertEquals(createdItem.getDescription(), gotItem.get().getDescription());
        }
        else throw (new NullPointerException());

    }

    @Test
    void getAllItemsTest(){
        ItemEntity item1 = new ItemEntity(null, "Drukarkanowiutka", "się psuje");
        ItemEntity item2 = new ItemEntity(null, "Wiertarkanowiutka", "wrrr");

        ItemEntity createdItem1 = itemService.createItem(item1);
        ItemEntity createdItem2 = itemService.createItem(item2);

        List<ItemEntity> items = itemService.getAllItems();

        assertEquals(createdItem1.getId(), items.get(0).getId());
        assertEquals(createdItem2.getId(), items.get(1).getId());
    }

}
