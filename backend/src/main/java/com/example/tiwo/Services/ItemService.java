package com.example.tiwo.Services;

import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemEntity createItem(ItemEntity itemEntity){
        return itemRepository.save(itemEntity);
    }

    public void deleteItem(Long id){
        itemRepository.delete(itemRepository.getReferenceById(id));
    }

    public Optional<ItemEntity> getItem(Long id){
        return itemRepository.findById(id);
    }

    public Optional<ItemEntity> getItem(String name){
        return itemRepository.getByName(name);
    }

    public List<ItemEntity> getAllItems(){
        return itemRepository.findAll();
    }
    public ItemEntity updateItem(Long id, ItemEntity itemEntity){
        Optional<ItemEntity> item = itemRepository.findById(id);
        if(item.isPresent()){
            ItemEntity ie = item.get();
            ie.setId(itemEntity.getId());
            ie.setName(itemEntity.getName());
            ie.setDescription(itemEntity.getDescription());

            return itemRepository.save(ie);
        }
        else{
            throw(new NullPointerException());
        }
    }

}
