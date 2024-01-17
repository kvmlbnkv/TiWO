package com.example.tiwo.Services;

import com.example.tiwo.Entities.ListEntity;
import com.example.tiwo.Entities.UserEntity;
import com.example.tiwo.Exceptions.NoSuchListException;
import com.example.tiwo.Exceptions.NoSuchUserException;
import com.example.tiwo.Exceptions.UserAlreadyRegisteredException;
import com.example.tiwo.Repositories.ListRepository;
import com.example.tiwo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    ListRepository listRepository;

    @Autowired
    public UserService(UserRepository userRepository, ListRepository listRepository) {
        this.userRepository = userRepository;
        this.listRepository = listRepository;
    }

    public UserEntity createUser(UserEntity userEntity){

        if (userRepository.getByUsername(userEntity.getUsername()).isPresent()){
            throw new UserAlreadyRegisteredException();
        }

        return userRepository.save(userEntity);
    }

    /*
    public void deleteUser(UserEntity userEntity){
        userRepository.delete(userEntity);
    }

    public void deleteUser(Long id){
        userRepository.delete(userRepository.getReferenceById(id));
    }

    public Optional<UserEntity> getUser(Long id){
        return userRepository.findById(id);
    }
    */
    public Optional<UserEntity> getUser(String name){
        return userRepository.getByUsername(name);
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public void addListToUser(Long listId, String username){
        Optional<UserEntity> user = userRepository.getByUsername(username);
        Optional<ListEntity> list = listRepository.findById(listId);

        if(user.isPresent()) {
            if (list.isPresent()) {
                user.get().getLists().add(list.get());
                list.get().setUser(user.get());
                listRepository.save(list.get());
                userRepository.save(user.get());
            }
            else {
                throw(new NoSuchListException());
            }
        }
        else{
            throw(new NoSuchUserException());
        }
    }

    public List<ListEntity> getLists(String username) {
        Optional<UserEntity> user = userRepository.getByUsername(username);
        if (user.isPresent()){
            return user.get().getLists();
        }
        else {
            throw(new NoSuchUserException());
        }
    }

    /*
    public UserEntity updateUser(Long id, UserEntity userEntity){
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()){
            UserEntity ue = user.get();
            ue.setId(userEntity.getId());
            ue.setUsername(userEntity.getUsername());
            ue.setEmail(userEntity.getEmail());
            ue.setPassword(userEntity.getPassword());

            return userRepository.save(ue);
        }
        else{
            throw(new NullPointerException());
        }
    }
    */
}
