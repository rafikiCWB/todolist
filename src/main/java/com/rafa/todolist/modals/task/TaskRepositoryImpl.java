package com.rafa.todolist.modals.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface TaskRepositoryImpl extends JpaRepository<TaskModel, UUID> {
}
