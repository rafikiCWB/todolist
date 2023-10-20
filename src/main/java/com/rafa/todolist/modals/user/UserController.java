package com.rafa.todolist.modals.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserRepositoryImpl userRepository;

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody @Valid UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @GetMapping()
    public List<UserModel> findAll() {
        List<UserModel> users = userRepository.findAll();
        return users;
    }

    @DeleteMapping()
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        userRepository.deleteById(id);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<Object> updateById(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserModel userModel) {
        Optional<UserModel> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        var user = userOptional.get();
        BeanUtils.copyProperties(userModel, user, "id");
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(user));
    }

}

