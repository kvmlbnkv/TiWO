package com.example.tiwo.Repositories;

import com.example.tiwo.Entities.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListRepository extends JpaRepository<ListEntity, Long> {
}
