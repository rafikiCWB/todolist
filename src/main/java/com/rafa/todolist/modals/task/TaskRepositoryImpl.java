package com.rafa.todolist.modals.task;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepositoryImpl extends JpaRepository<TaskModel, UUID> {

    List<TaskModel> findByIdUser(UUID user);
}
