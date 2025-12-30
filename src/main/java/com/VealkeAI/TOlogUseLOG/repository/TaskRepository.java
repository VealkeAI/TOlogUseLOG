package com.VealkeAI.TOlogUseLOG.repository;

import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("""
            SELECT u.shiftUTC FROM UserEntity u
                WHERE u.tgId = :userId
            """)
    Optional<Integer> getUserShiftUTC(@Param("userId") Long userId);

    @Query("""
            SELECT t FROM TaskEntity t
                WHERE (:tgId IS NULL OR t.user.tgId = :tgId)
                AND (:priority IS NULL OR t.priority = :priority)
                AND (:state IS NULL OR t.state = :state)
            """)
    Page<TaskEntity> getTasksByFilter(@Param("tgId") Long tgId,
                                      @Param("priority") PriorityStatus priority,
                                      @Param("state") State state,
                                      Pageable pageable
    );
}
