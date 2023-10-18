package com.rafa.todolist.modals.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepositoryImpl extends JpaRepository<UserModel, UUID> {

    UserModel findByUsername(String username);
}

