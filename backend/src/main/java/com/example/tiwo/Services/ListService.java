package com.example.tiwo.Services;

import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Repositories.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListService {
    ListRepository listRepository;

    @Autowired
    public ListService(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public ListEntity createList(ListEntity listEntity){
        return listRepository.save(listEntity);
    }

    public void deleteList(ListEntity listEntity){
        listRepository.delete(listEntity);
    }

    public Optional<ListEntity> getList(Long id){
        return listRepository.findById(id);
    }

    public ListEntity updateList(Long id, ListEntity listEntity){
        Optional<ListEntity> list = listRepository.findById(id);
        if(list.isPresent()){
            ListEntity le = list.get();
            le.setId(listEntity.getId());
            le.setUserId(listEntity.getUserId());
            le.setDate(listEntity.getDate());

            return listRepository.save(le);
        }
        else{
            throw(new NullPointerException());
        }
    }

}