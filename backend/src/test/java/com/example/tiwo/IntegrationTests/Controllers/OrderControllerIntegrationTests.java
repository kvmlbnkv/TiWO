package com.example.tiwo.IntegrationTests.Controllers;


import com.example.tiwo.DTOs.LoginDTO;
import com.example.tiwo.DTOs.RegistrationDTO;
import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Repositories.ItemRepository;
import com.example.tiwo.Repositories.ListRepository;
import com.example.tiwo.Repositories.OrderRepository;
import com.example.tiwo.Repositories.UserRepository;
import com.example.tiwo.Services.ItemService;
import com.example.tiwo.Services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc()
public class OrderControllerIntegrationTests {


    @Autowired
    UserRepository userRepository;

    @Autowired
    ListRepository listRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;


    @AfterEach
    void afterEach(){
        userRepository.deleteAll();
        listRepository.deleteAll();
        orderRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    void addOrderTest() throws Exception {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setEmail("test@test.pl");
        dto.setUsername("test");
        dto.setPassword("test");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().is(200));


        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test");
        logindto.setPassword("test");

        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");


        result = mockMvc.perform(post("/tiwo/list/add?username=test&name=testowa&date=2023-01-25").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andReturn();


        Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        itemService.createItem(new ItemEntity(null, "Drukarkanowiutka", "się psuje też"));

        mockMvc.perform(post("/tiwo/order/add?listId="+id+"&item=Drukarkanowiutka&amount=1&grammage=sztuka").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(200));


    }

    @Test
    void addOrderExceptionTest() throws Exception {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setEmail("test@test.pl");
        dto.setUsername("test");
        dto.setPassword("test");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().is(200));


        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test");
        logindto.setPassword("test");

        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");


        result = mockMvc.perform(post("/tiwo/list/add?username=test&name=testowa&date=2023-01-25").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andReturn();


        Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        itemService.createItem(new ItemEntity(null, "Drukarkanowiutka", "się psuje też"));

        mockMvc.perform(post("/tiwo/order/add?listId="+id+"&item=Drukarkanowiutkajednaknie&amount=1&grammage=sztuka").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(404));


    }

    @Test
    void toggleRealizedTest() throws Exception {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setEmail("test@test.pl");
        dto.setUsername("test3");
        dto.setPassword("test3");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().is(200));


        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test3");
        logindto.setPassword("test3");

        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");


        result = mockMvc.perform(post("/tiwo/list/add?username=test3&name=testowa3&date=2023-01-25").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andReturn();


        Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        itemService.createItem(new ItemEntity(null, "Drukarkanowiusienka", "się psuje też"));

        result = mockMvc.perform(post("/tiwo/order/add?listId="+id+"&item=Drukarkanowiusienka&amount=1&grammage=sztuka").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andReturn();

        id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");


        mockMvc.perform(put("/tiwo/order/toggleRealized?orderId="+id).contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(200));


    }

    @Test
    void toggleRealizedExceptionTest() throws Exception {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setEmail("test@test.pl");
        dto.setUsername("test3");
        dto.setPassword("test3");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().is(200));


        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test3");
        logindto.setPassword("test3");

        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");


        result = mockMvc.perform(post("/tiwo/list/add?username=test3&name=testowa3&date=2023-01-25").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andReturn();


        Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        itemService.createItem(new ItemEntity(null, "Drukarkanowiusienka", "się psuje też"));

        result = mockMvc.perform(post("/tiwo/order/add?listId="+id+"&item=Drukarkanowiusienka&amount=1&grammage=sztuka").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andReturn();

        id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");


        mockMvc.perform(put("/tiwo/order/toggleRealized?orderId=32478789327489").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(404));


    }
}
