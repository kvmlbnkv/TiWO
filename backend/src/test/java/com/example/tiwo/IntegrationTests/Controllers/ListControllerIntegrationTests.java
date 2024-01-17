package com.example.tiwo.IntegrationTests.Controllers;

import com.example.tiwo.DTOs.LoginDTO;
import com.example.tiwo.DTOs.RegistrationDTO;
import com.example.tiwo.Repositories.ItemRepository;
import com.example.tiwo.Repositories.ListRepository;
import com.example.tiwo.Repositories.OrderRepository;
import com.example.tiwo.Repositories.UserRepository;
import com.example.tiwo.Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc()
public class ListControllerIntegrationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListRepository listRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

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
    void addListTest() throws Exception {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setEmail("test@test.pl");
        dto.setUsername("test1");
        dto.setPassword("test1");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().is(200));


        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test1");
        logindto.setPassword("test1");

        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");


        mockMvc.perform(post("/tiwo/list/add?username=test1&name=testowa&date=2023-01-25").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(200));


    }

    @Test
    void addListExceptionTest() throws Exception {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setEmail("test@test.pl");
        dto.setUsername("test1");
        dto.setPassword("test1");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().is(200));


        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test1");
        logindto.setPassword("test1");

        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");


        mockMvc.perform(post("/tiwo/list/add?username=test1321&name=testowa&date=2023-01-25").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(404));


    }


    @Test
    void deleteList() throws Exception {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setEmail("test@test.pl");
        dto.setUsername("test2");
        dto.setPassword("test2");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().is(200));


        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test2");
        logindto.setPassword("test2");

        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");


        result = mockMvc.perform(post("/tiwo/list/add?username=test2&name=testowa&date=2023-01-25").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andReturn();

        Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(delete("/tiwo/list/delete?listId="+id).contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(200));

    }

    @Test
    void updateListTest() throws Exception {
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

        mockMvc.perform(put("/tiwo/list/update?listId="+id+"&name=nowatestowa&date=2023-01-26").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(200));

    }

    @Test
    void updateListExceptionTest() throws Exception {
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

        mockMvc.perform(put("/tiwo/list/update?listId=2109480129348&name=nowatestowa&date=2023-01-26").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(404));

    }
}
