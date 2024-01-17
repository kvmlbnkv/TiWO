package com.example.tiwo.IntegrationTests.Services;


import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Repositories.ListRepository;
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
import com.example.tiwo.Entities.UserEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceIntegrationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListRepository listRepository;

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
    }

    @Test
    void createUserTest(){
        UserEntity user = new UserEntity(null, "test", "test@test.pl", "test", null);

        userService.createUser(user);

        Optional<UserEntity> optionalAddedUser = userRepository.getByUsername("test");

        if (optionalAddedUser.isPresent()) {
            UserEntity addedUser = optionalAddedUser.get();
            assertNotNull(addedUser);
            assertEquals(user.getId(), addedUser.getId());
            assertEquals(user.getEmail(), addedUser.getEmail());
            assertEquals(user.getUsername(), addedUser.getUsername());
            assertEquals(user.getPassword(), addedUser.getPassword());
        }
    }

    @Test
    void getUserTest(){
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());

        userService.createUser(user);

        Optional<UserEntity> gotUser = userService.getUser("test");

        if (gotUser.isPresent()){
            assertEquals(user.getId(), gotUser.get().getId());
            assertEquals(user.getEmail(), gotUser.get().getEmail());
            assertEquals(user.getUsername(), gotUser.get().getUsername());
            assertEquals(user.getPassword(), gotUser.get().getPassword());
        }
        else {
            throw(new NullPointerException());
        }
    }

    @Test
    void addListToUserTest() throws ParseException {
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));

        UserEntity createdUser = userService.createUser(user);
        ListEntity createdList = listService.createList(list);

        userService.addListToUser(createdList.getId(), createdUser.getUsername());

        Optional<UserEntity> gotUser = userService.getUser(user.getUsername());
        Optional<ListEntity> gotList = listService.getList(list.getId());

        if (gotUser.isPresent() && gotList.isPresent()) {
            assertEquals(createdUser.getId(), gotList.get().getUser().getId());
            assertEquals(createdList.getId(), gotUser.get().getLists().get(0).getId());
        }
        else throw (new NullPointerException());

    }

    @Test
    void getAllUsersTest(){
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());

        UserEntity createdUser = userService.createUser(user);

        List<UserEntity> allUsers = userService.getAllUsers();

        assertEquals(allUsers.get(allUsers.size()-1).getId(), createdUser.getId());
    }

    @Test
    void getListsTest() throws ParseException {
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());

        ListEntity list1 = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));
        ListEntity list2 = new ListEntity(null, "testowa2", null, new ArrayList<>(), formatter.parse("26-01-2023"));

        UserEntity createdUser = userService.createUser(user);
        ListEntity createdList1 = listService.createList(list1);
        ListEntity createdList2 = listService.createList(list2);

        userService.addListToUser(createdList1.getId(), createdUser.getUsername());
        userService.addListToUser(createdList2.getId(), createdUser.getUsername());

        Optional<UserEntity> gotUser = userService.getUser(createdUser.getUsername());
        List<ListEntity> lists = userService.getLists(createdUser.getUsername());

        if (gotUser.isPresent()){
            assertEquals(gotUser.get().getLists().get(0).getId(), lists.get(0).getId());
            assertEquals(gotUser.get().getLists().get(1).getId(), lists.get(1).getId());
        }
        else throw (new NullPointerException());
    }

}
