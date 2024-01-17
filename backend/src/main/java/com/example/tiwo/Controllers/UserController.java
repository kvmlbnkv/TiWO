package com.example.tiwo.Controllers;


import com.example.tiwo.DTOs.LoginDTO;
import com.example.tiwo.DTOs.RegistrationDTO;
import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Entities.UserEntity;
import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Services.ItemService;
import com.example.tiwo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tiwo/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody()RegistrationDTO dto){
        final UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setLists(new ArrayList<>());

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    /*
    @RequestMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam() Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @GetMapping("/getAll")
    public ResponseEntity<List<UserEntity>> getAll(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestBody LoginDTO dto){
        Optional<UserEntity> user = userService.getUser(dto.getUsername());
        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (passwordEncoder.matches(user.get().getPassword(), dto.getPassword())){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/lists")
    public ResponseEntity<List<ListEntity>> getLists(@RequestParam() String username){
        Optional<UserEntity> user = userService.getUser(username);
        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.getLists(username), HttpStatus.OK);
    }
}
