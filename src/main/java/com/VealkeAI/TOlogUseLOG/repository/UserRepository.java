package com.VealkeAI.TOlogUseLOG.repository;

import com.VealkeAI.TOlogUseLOG.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByTgId(Long id);
}
