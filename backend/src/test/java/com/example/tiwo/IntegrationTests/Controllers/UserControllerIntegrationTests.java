package com.example.tiwo.IntegrationTests.Controllers;


import com.example.tiwo.Controllers.UserController;
import com.example.tiwo.DTOs.LoginDTO;
import com.example.tiwo.DTOs.RegistrationDTO;
import com.example.tiwo.Entities.UserEntity;
import com.example.tiwo.Repositories.ItemRepository;
import com.example.tiwo.Repositories.ListRepository;
import com.example.tiwo.Repositories.OrderRepository;
import com.example.tiwo.Repositories.UserRepository;
import com.example.tiwo.Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc()
public class UserControllerIntegrationTests {


    @Autowired
    UserRepository userRepository;

    @Autowired
    ListRepository listRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    private UserService userService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void afterEach(){
        userRepository.deleteAll();
        listRepository.deleteAll();
        orderRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void LoginTest() throws Exception {
        RegistrationDTO registrationdto = new RegistrationDTO();
        registrationdto.setEmail("test@test.pl");
        registrationdto.setUsername("test");
        registrationdto.setPassword("test");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationdto)));

        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test");
        logindto.setPassword("test");

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andExpect(status().is(200));
    }

    @Test
    void LoginTestException() throws Exception {
        RegistrationDTO registrationdto = new RegistrationDTO();
        registrationdto.setEmail("test@test.pl");
        registrationdto.setUsername("test");
        registrationdto.setPassword("test");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationdto)));

        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test");
        logindto.setPassword("testzlehaslo");

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andExpect(status().is(401));
    }


    @Test
    void registrationTest() throws Exception {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setEmail("test@test.pl");
        dto.setUsername("test");
        dto.setPassword("test");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().is(200));

    }

    @Test
    void registrationExceptionTest() throws Exception {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setEmail("test@test.pl");
        dto.setUsername("test");
        dto.setPassword("test");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().is(200));

        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().is(409));
    }

    @Test
    void getListsTest() throws Exception {
        RegistrationDTO registrationdto = new RegistrationDTO();
        registrationdto.setEmail("test@test.pl");
        registrationdto.setUsername("test");
        registrationdto.setPassword("test");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationdto)));

        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test");
        logindto.setPassword("test");

        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");

        mockMvc.perform(get("/tiwo/user/lists?username=test").contentType(MediaType.APPLICATION_JSON).header("authorization", token)).andExpect(status().is(200));
    }


    @Test
    void getListsExceptionTest() throws Exception {
        RegistrationDTO registrationdto = new RegistrationDTO();
        registrationdto.setEmail("test@test.pl");
        registrationdto.setUsername("test");
        registrationdto.setPassword("test");
        mockMvc.perform(post("/tiwo/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationdto)));

        LoginDTO logindto = new LoginDTO();
        logindto.setUsername("test");
        logindto.setPassword("test");

        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logindto))).andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");

        mockMvc.perform(get("/tiwo/user/lists?username=testniema").contentType(MediaType.APPLICATION_JSON).header("authorization", token)).andExpect(status().is(404));
    }


}
