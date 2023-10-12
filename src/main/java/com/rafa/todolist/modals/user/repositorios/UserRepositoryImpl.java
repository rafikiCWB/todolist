package com.rafa.todolist.modals.user.repositorios;

import com.rafa.todolist.modals.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepositoryImpl extends JpaRepository<UserModel, UUID> {

    UserModel findByUsername(String username);
}

