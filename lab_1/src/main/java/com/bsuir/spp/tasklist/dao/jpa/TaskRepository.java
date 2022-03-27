package com.bsuir.spp.tasklist.dao.jpa;

import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.dao.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Arrays;
import java.util.List;

/**
 * DAO for Task entity
 */
public interface TaskRepository extends JpaRepository<Task, Long>,
        JpaSpecificationExecutor<Task> {

    List<Task> findAllByStatusId(Long status);

    List<Task> findAllByUserId(Long userId);

    List<Task> findAllByUserIdAndStatusId(Long userId, Long status);
}
