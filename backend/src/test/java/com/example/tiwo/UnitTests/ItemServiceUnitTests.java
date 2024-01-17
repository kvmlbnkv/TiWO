package com.example.tiwo.UnitTests;

import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Repositories.ItemRepository;
import com.example.tiwo.Services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ItemServiceUnitTests {

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;


    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createItemTest(){
        ItemEntity item = new ItemEntity(null, "Drukarka", "się psuje");

        when(itemRepository.save(item)).thenReturn(item);

        ItemEntity createdItem = itemService.createItem(item);

        assertNotNull(createdItem);
        assertEquals(item.getId(), createdItem.getId());
        assertEquals(item.getName(), createdItem.getName());
        assertEquals(item.getDescription(), createdItem.getDescription());
    }

    @Test
    void getItemTest(){
        ItemEntity item = new ItemEntity(null, "Drukarka", "się psuje");

        when(itemRepository.getByName(item.getName())).thenReturn(Optional.of(item));

        Optional<ItemEntity> gotItem = itemService.getItem(item.getName());

        if (gotItem.isPresent()){
            assertEquals(item.getId(), gotItem.get().getId());
            assertEquals(item.getName(), gotItem.get().getName());
            assertEquals(item.getDescription(), gotItem.get().getDescription());
        }

    }

    @Test
    void getAllItemsTest(){
        ItemEntity item1 = new ItemEntity(null, "Drukarka", "się psuje");
        ItemEntity item2 = new ItemEntity(null, "Wiertarka", "wrrr");


        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<ItemEntity> items = itemService.getAllItems();

        assertEquals(item1, items.get(0));
        assertEquals(item2, items.get(1));
    }

}
