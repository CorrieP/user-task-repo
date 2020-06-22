package com.black.swan.assessment.repository;

import com.black.swan.assessment.dto.task.OutputTaskDto;
import com.black.swan.assessment.persistence.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT new com.black.swan.assessment.dto.task.OutputTaskDto(t.id, t.name, t.description, t.dateTime, t.status) FROM Task t WHERE t.user.id = :userId")
    List<OutputTaskDto> findTasksByUserId(Long userId);

    @Query("SELECT new com.black.swan.assessment.dto.task.OutputTaskDto(t.id, t.name, t.description, t.dateTime, t.status) FROM Task t WHERE t.id = :taskId and t.user.id = :userId")
    Optional<OutputTaskDto> findTaskByUserAndTaskId(Long userId, Long taskId);

    @Query("SELECT t FROM Task t WHERE t.status = com.black.swan.assessment.persistence.TaskStatus.PENDING and t.dateTime <= :dateTime")
    List<Task> findPendingExpiredTasks(OffsetDateTime dateTime);

}
