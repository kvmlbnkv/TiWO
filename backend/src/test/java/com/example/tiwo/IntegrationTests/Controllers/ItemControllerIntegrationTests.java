package com.example.tiwo.IntegrationTests.Controllers;

import com.example.tiwo.DTOs.LoginDTO;
import com.example.tiwo.DTOs.RegistrationDTO;
import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Repositories.ItemRepository;
import com.example.tiwo.Repositories.ListRepository;
import com.example.tiwo.Repositories.OrderRepository;
import com.example.tiwo.Repositories.UserRepository;
import com.example.tiwo.Services.ItemService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc()
public class ItemControllerIntegrationTests {

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
    void getAllTest() throws Exception {
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

        mockMvc.perform(post("/tiwo/item/add?name=drukareczka&description=eeeeeeee").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(200));

        mockMvc.perform(get("/tiwo/item/getAll").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(200));

    }

    @Test
    void getByName() throws Exception {
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

        mockMvc.perform(post("/tiwo/item/add?name=drukareczka&description=eeeeeeee").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(200));

        mockMvc.perform(get("/tiwo/item/getByName?name=drukareczka").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(200));

    }

    @Test
    void getByNameExceptionTest() throws Exception {
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

        mockMvc.perform(post("/tiwo/item/add?name=drukareczka&description=eeeeeeee").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(200));

        mockMvc.perform(get("/tiwo/item/getByName?name=drukareczkauauauau").contentType(MediaType.APPLICATION_JSON)
                .header("authorization", token)).andExpect(status().is(404));

    }
}
