package com.example.tiwo.UnitTests;

import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Entities.UserEntity;
import com.example.tiwo.Exceptions.NoSuchListException;
import com.example.tiwo.Exceptions.NoSuchOrderException;
import com.example.tiwo.Repositories.ListRepository;
import com.example.tiwo.Repositories.OrderRepository;
import com.example.tiwo.Repositories.UserRepository;
import com.example.tiwo.Services.ListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ListServiceUnitTests {

    @Mock
    ListRepository listRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    ListService listService;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.GERMAN);


    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createListTest() throws ParseException {
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));

        when(listRepository.save(list)).thenReturn(list);

        ListEntity createdList = listService.createList(list);

        assertNotNull(createdList);
        assertEquals(list.getId(), createdList.getId());
        assertEquals(list.getUser(), createdList.getUser());
        assertEquals(list.getOrders(), createdList.getOrders());
        assertEquals(list.getName(), createdList.getName());
        assertEquals(list.getDate(), createdList.getDate());
    }

    @Test
    void deleteListTest() throws ParseException {
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());
        ListEntity list = new ListEntity(null, "testowa", user, new ArrayList<>(), formatter.parse("25-01-2023"));

        when(listRepository.getReferenceById(list.getId())).thenReturn(list);

        listService.deleteList(list.getId());

        Mockito.verify(listRepository).delete(list);


    }


    @Test
    void addOrderToListTest() throws ParseException {
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(listRepository.findById(list.getId())).thenReturn(Optional.of(list));

        listService.addOrderToList(order.getId(), list.getId());

        assertEquals(list, order.getList());
        assertEquals(order, list.getOrders().get(0));

    }

    @Test
    void addOrderToListListExceptionTest() throws ParseException {
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(listRepository.findById(list.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchListException.class, ()->listService.addOrderToList(order.getId(), list.getId()));

    }

    @Test
    void addOrderToListOrderExceptionTest() throws ParseException {
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));
        OrderEntity order = new OrderEntity(null, null, null, 1, "sztuka", false);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());
        when(listRepository.findById(list.getId())).thenReturn(Optional.of(list));

        assertThrows(NoSuchOrderException.class, ()->listService.addOrderToList(order.getId(), list.getId()));

    }


    @Test
    void updateListTest() throws ParseException {
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));

        when(listRepository.findById(list.getId())).thenReturn(Optional.of(list));

        listService.updateList(list.getId(), "testowaNowa", formatter.parse("26-02-2023"));

        assertEquals("testowaNowa", list.getName());
        assertEquals(formatter.parse("26-02-2023"), list.getDate());

    }

    @Test
    void updateListListExceptionTest() throws ParseException {
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));

        when(listRepository.findById(list.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchListException.class, ()->listService.updateList(list.getId(), "testowaNowa", formatter.parse("26-02-2023")));

    }


}
