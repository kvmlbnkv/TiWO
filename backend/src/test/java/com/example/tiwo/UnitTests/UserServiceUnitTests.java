package com.example.tiwo.UnitTests;

import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Entities.UserEntity;
import com.example.tiwo.Exceptions.NoSuchListException;
import com.example.tiwo.Exceptions.NoSuchUserException;
import com.example.tiwo.Exceptions.UserAlreadyRegisteredException;
import com.example.tiwo.Repositories.ListRepository;
import com.example.tiwo.Repositories.UserRepository;
import com.example.tiwo.Services.UserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;

public class UserServiceUnitTests {

    @Mock
    UserRepository userRepository;

    @Mock
    ListRepository listRepository;

    @InjectMocks
    UserService userService;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.GERMAN);

    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserTest(){
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());

        when(userRepository.save(user)).thenReturn(user);

        UserEntity createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getPassword(), createdUser.getPassword());
        assertEquals(user.getLists(), createdUser.getLists());
    }

    @Test
    void createUserExceptionTest(){
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());

        when(userRepository.getByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyRegisteredException.class, () -> userService.createUser(user));
    }

    @Test
    void getUserTest(){
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());

        when(userRepository.getByUsername("test")).thenReturn(Optional.of(user));

        Optional<UserEntity> gotUser = userService.getUser("test");

        if (gotUser.isPresent()){
            assertEquals(user.getId(), gotUser.get().getId());
            assertEquals(user.getEmail(), gotUser.get().getEmail());
            assertEquals(user.getUsername(), gotUser.get().getUsername());
            assertEquals(user.getPassword(), gotUser.get().getPassword());
            assertEquals(user.getLists(), gotUser.get().getLists());
        }
    }

    @Test
    void getAllUsersTest(){
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserEntity> allUsers = userService.getAllUsers();

        assertEquals(user.getId(), allUsers.get(0).getId());
        assertEquals(user.getEmail(), allUsers.get(0).getEmail());
        assertEquals(user.getUsername(), allUsers.get(0).getUsername());
        assertEquals(user.getPassword(), allUsers.get(0).getPassword());
        assertEquals(user.getLists(), allUsers.get(0).getLists());
    }

    @Test
    void addListToUserTest() throws ParseException {
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));

        when(userRepository.getByUsername("test")).thenReturn(Optional.of(user));
        when(listRepository.findById(list.getId())).thenReturn(Optional.of(list));

        userService.addListToUser(list.getId(), "test");

        assertEquals(user, list.getUser());
        assertEquals(list, user.getLists().get(0));
    }

    @Test
    void addListToUserUserExceptionTest() throws ParseException {
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));

        when(userRepository.getByUsername("test")).thenReturn(Optional.empty());
        when(listRepository.findById(list.getId())).thenReturn(Optional.of(list));

        assertThrows(NoSuchUserException.class, () -> userService.addListToUser(list.getId(), "test"));

    }

    @Test
    void addListToUserListExceptionTest() throws ParseException {
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", new ArrayList<>());
        ListEntity list = new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023"));

        when(userRepository.getByUsername("test")).thenReturn(Optional.of(user));
        when(listRepository.findById(list.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchListException.class, () -> userService.addListToUser(list.getId(), "test"));

    }

    @Test
    void getListsTest() throws ParseException {
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", Arrays.asList(
                new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023")),
                new ListEntity(null, "testowa2", null, new ArrayList<>(), formatter.parse("26-01-2023"))));

        when(userRepository.getByUsername("test")).thenReturn(Optional.of(user));

        List<ListEntity> lists = userService.getLists("test");

        assertEquals(user.getLists().get(0), lists.get(0));
        assertEquals(user.getLists().get(1), lists.get(1));
    }

    @Test
    void getListsUserExceptionTest() throws ParseException {
        UserEntity user = new UserEntity(null, "test", "test@test.com", "test", Arrays.asList(
                new ListEntity(null, "testowa", null, new ArrayList<>(), formatter.parse("25-01-2023")),
                new ListEntity(null, "testowa2", null, new ArrayList<>(), formatter.parse("26-01-2023"))));

        when(userRepository.getByUsername("test")).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> userService.getLists("test"));
    }

}
