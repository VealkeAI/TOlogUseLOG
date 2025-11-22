package com.VealkeAI.TOlogUseLOG.repository;

import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("""
            SELECT t from TaskEntity t
                WHERE :userId = t.user.id
            """)
    List<TaskEntity> getAllUserTask(@Param("userId") Long userId);

    @Query("""
            SELECT u.shiftUTC FROM UserEntity u
            WHERE u.id = :userId
            """)
    Integer getUserShiftUTC(@Param("userId") Long userId);
}
