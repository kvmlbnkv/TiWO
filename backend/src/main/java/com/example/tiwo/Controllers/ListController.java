package com.example.tiwo.Controllers;

import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Services.ListService;
import com.example.tiwo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/tiwo/list")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ListController {

    private final ListService listService;
    private final UserService userService;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH);

    @PostMapping("/add")
    public ResponseEntity<ListEntity> addList(@RequestParam() String username, @RequestParam() String name, @RequestParam()String date) throws ParseException {
        ListEntity list = listService.createList(new ListEntity(null, name, null, new ArrayList<>(), formatter.parse(date)));
        userService.addListToUser(list.getId(), username);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteList(@RequestParam() Long listId){
        listService.deleteList(listId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ListEntity> updateList(@RequestParam Long listId, @RequestParam() String name, @RequestParam() String date) throws ParseException {
        return new ResponseEntity<>(listService.updateList(listId, name, formatter.parse(date)), HttpStatus.OK);
    }

}
