package com.VealkeAI.TOlogUseLOG.repository;

import com.VealkeAI.TOlogUseLOG.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByTgId(Long id);
    @Modifying
    @Transactional
    @Query("""
            UPDATE u FROM
            SET u.shiftUTC = :shift
            WHERE :tgId = u.tgId
            """)
    void switchShiftUTC(@Param("tgId") Long tgId,
                       @Param("shift") Integer shift);
}
