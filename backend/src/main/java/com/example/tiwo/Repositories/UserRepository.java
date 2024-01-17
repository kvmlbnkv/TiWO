package com.example.tiwo.Repositories;

import com.example.tiwo.Entities.ItemEntity;
import com.example.tiwo.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> getByUsername(String name);

}
