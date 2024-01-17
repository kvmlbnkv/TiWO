package com.example.tiwo;

import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Entities.UserEntity;
import com.example.tiwo.Services.ItemService;
import com.example.tiwo.Services.ListService;
import com.example.tiwo.Services.OrderService;
import com.example.tiwo.Services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

@SpringBootApplication
public class TiWoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiWoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, ListService listService, OrderService orderService, ItemService itemService) {
        return args -> {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);

            userService.createUser(new UserEntity(null, "admin", "admin@admin.com", passwordEncoder.encode("admin"), new ArrayList<>()));
            itemService.createItem(new ItemEntity(null, "Wiertarka", "wrrr"));
            itemService.createItem(new ItemEntity(null, "Chleb", "świezy"));
            itemService.createItem(new ItemEntity(null, "Drukarka", "się psuje"));
            listService.createList(new ListEntity(null, "na już", null, new ArrayList<>(), formatter.parse("25-01-2023")));
            //listService.createList(ListEntity.builder().name("na już").orders(Collections.emptyList()).date(formatter.parse("25-01-2023")).build());

            orderService.createOrder(new OrderEntity(null, null, null, 1, "sztuka", false));
            //orderService.getOrder(4L).get().setItem(itemService.getItem(2L).get());
            orderService.addItem("Wiertarka", 6L);
            //orderService.getOrder(4L).get().setList(listService.getList(3L).get());

            orderService.createOrder(new OrderEntity(null, null, null, 2, "sztuka", false));
            orderService.addItem("Chleb", 7L);
            orderService.createOrder(new OrderEntity(null, null, null, 1, "sztuka", false));
            orderService.addItem("Drukarka", 8L);

            listService.createList(new ListEntity(null, "ważne", null, new ArrayList<>(), formatter.parse("21-12-2023")));

            listService.addOrderToList(6L, 5L);
            listService.addOrderToList(7L, 9L);
            listService.addOrderToList(8L, 5L);

            userService.addListToUser(5L, "admin");
            userService.addListToUser(9L, "admin");
            //listService.getList(3L).get().setUser(userService.getUser(1L).get());
            //listService.addUserToList("admin", 3L);

            itemService.createItem(new ItemEntity(null, "Ser", "dziurawy"));
            itemService.createItem(new ItemEntity(null, "Baton", "czekoladowy"));
            itemService.createItem(new ItemEntity(null, "Klawiatura", "mechaniczna"));


        };
    }
}
