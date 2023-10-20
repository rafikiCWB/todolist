package com.rafa.todolist.modals.task;

import com.rafa.todolist.modals.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepositoryImpl taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) throws Exception {
        // TODO: process POST request
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        // 10/11/2023 - Current
        // 10/10/2023 - startAt
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início / data de término deve ser maior do que a data atual");
        }
        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio deve ser menor que a data de término");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @GetMapping("/{id}")
    public Optional<TaskModel> listById(@PathVariable UUID id) {
        var tasks = this.taskRepository.findById(id);
        return tasks;
    }

    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var task = this.taskRepository.findById(id).orElse(null);
        Utils.getNonNullProperties(taskModel, task);
        return this.taskRepository.save(taskModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional> deleteById(@PathVariable(value = "id") UUID id) {
        Optional<TaskModel> task = this.taskRepository.findById(id);
        if (task.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não existe");
        } else {
            this.taskRepository.delete(task.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

}
