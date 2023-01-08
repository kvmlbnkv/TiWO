package com.example.tiwo.Services;

import com.example.tiwo.Entities.UserEntity;
import com.example.tiwo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    public void deleteUser(UserEntity userEntity){
        userRepository.delete(userEntity);
    }

    public Optional<UserEntity> getUser(Long id){
        return userRepository.findById(id);
    }

    public UserEntity updateUser(Long id, UserEntity userEntity){
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()){
            UserEntity ue = user.get();
            ue.setId(userEntity.getId());
            ue.setName(userEntity.getName());
            ue.setEmail(userEntity.getEmail());
            ue.setPassword(userEntity.getPassword());

            return userRepository.save(ue);
        }
        else{
            throw(new NullPointerException());
        }
    }
}
