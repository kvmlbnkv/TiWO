package com.example.tiwo.Controllers;


import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tiwo/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/add")
    public ResponseEntity<ItemEntity> create(@RequestParam() String name, @RequestParam() String description){
        final ItemEntity item = new ItemEntity();
        item.setName(name);
        item.setDescription(description);

        return new ResponseEntity<>(itemService.createItem(item), HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam() Long id){
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<ItemEntity>> getAll(){
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("getByName")
    public ResponseEntity<ItemEntity> getByName(@RequestParam() String name){
        Optional<ItemEntity> item = itemService.getItem(name);
        if (item.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item.get(), HttpStatus.OK);
    }
}
