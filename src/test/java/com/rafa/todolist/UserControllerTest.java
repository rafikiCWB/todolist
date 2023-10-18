package com.rafa.todolist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rafa.todolist.modals.user.UserController;
import com.rafa.todolist.modals.user.UserModel;
import com.rafa.todolist.modals.user.UserRepositoryImpl;

public class UserControllerTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserModel userModel = new UserModel();
        userModel.setUsername("testuser");
        userModel.setPassword("testpassword");

        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        ResponseEntity responseEntity = userController.createUser(userModel);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(userModel, responseEntity.getBody());
    }

    @Test
    public void testCreateUserAlreadyExists() {
        UserModel userModel = new UserModel();
        userModel.setUsername("testuser");
        userModel.setPassword("testpassword");

        when(userRepository.findByUsername("testuser")).thenReturn(userModel);

        ResponseEntity responseEntity = userController.createUser(userModel);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Usuário já existe", responseEntity.getBody());
    }

    @Test
    public void testFindAll() {
        List<UserModel> users = new ArrayList<>();
        UserModel userModel = new UserModel();
        userModel.setUsername("testuser");
        userModel.setPassword("testpassword");
        users.add(userModel);

        when(userRepository.findAll()).thenReturn(users);

        List<UserModel> result = userController.findAll();

        assertEquals(users, result);
    }

    @Test
    public void testDeleteAll() {
        userController.deleteAll();
    }

    @Test
    public void testDeleteById() {
        UUID id = UUID.randomUUID();
        userController.deleteById(id);
    }

    @Test
    public void testUpdateById() {
        UUID id = UUID.randomUUID();
        UserModel userModel = new UserModel();
        userModel.setUsername("testuser");
        userModel.setPassword("testpassword");

        Optional<UserModel> userOptional = Optional.of(userModel);

        when(userRepository.findById(id)).thenReturn(userOptional);
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        ResponseEntity responseEntity = userController.updateById(id, userModel);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userModel, responseEntity.getBody());
    }

    @Test
    public void testUpdateByIdNotFound() {
        UUID id = UUID.randomUUID();
        UserModel userModel = new UserModel();
        userModel.setUsername("testuser");
        userModel.setPassword("testpassword");

        Optional<UserModel> userOptional = Optional.empty();

        when(userRepository.findById(id)).thenReturn(userOptional);

        ResponseEntity responseEntity = userController.updateById(id, userModel);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Usuário não encontrado", responseEntity.getBody());
    }
}