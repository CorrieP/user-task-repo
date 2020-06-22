package com.black.swan.assessment.controller;

import com.black.swan.assessment.dto.task.InputTaskDto;
import com.black.swan.assessment.dto.task.OutputTaskDto;
import com.black.swan.assessment.dto.task.UpdateTaskDto;
import com.black.swan.assessment.persistence.Task;
import com.black.swan.assessment.repository.TaskRepository;
import com.black.swan.assessment.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController("api/task")
public class TaskController {
    private final TaskRepository repository;
    private final UserRepository userRepo;

    TaskController(TaskRepository repository, UserRepository userRepo) {
        this.repository = repository;
        this.userRepo = userRepo;
    }

    @GetMapping("/user/{user_id}/task")
    List<OutputTaskDto> findAllTasksForUser(@PathVariable Long user_id) {
        return repository.findTasksByUserId(user_id);
    }

    @GetMapping("/user/{user_id}/task/{task_id}")
    OutputTaskDto findTaskInfoForUser(@PathVariable Long user_id, @PathVariable Long task_id) {
        return repository.findTaskByUserAndTaskId(user_id,task_id)
                .orElseThrow(() -> new UserTaskNotFoundException(user_id, task_id));
    }

    @PostMapping("/user/{user_id}/task")
    OutputTaskDto newTask(@RequestBody InputTaskDto newTask, @PathVariable Long user_id) {
        var user = userRepo.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException(user_id));
        Task task = newTask.toTaskConvert();
        task.user = user;
        return repository.save(task).toTaskOutputConvert();
    }

    @PutMapping("/user/{user_id}/task/{task_id}")
    OutputTaskDto updateTask(@RequestBody UpdateTaskDto updateTask, @PathVariable Long user_id, @PathVariable Long task_id) {
        if (userRepo.findById(user_id) == null ) throw new UserNotFoundException(user_id);
        return repository.findById(task_id)
                .map(task -> {
                    task.modified = OffsetDateTime.now();
                    task.name = updateTask.name;
                    return repository.save(task).toTaskOutputConvert();
                })
                .orElseThrow(() -> new TaskNotFoundException(task_id));
    }

    @DeleteMapping("/user/{user_id}/task/{task_id}")
    @ResponseStatus( HttpStatus.OK )
    ResponseEntity deleteTask(@PathVariable Long user_id, @PathVariable Long task_id) {
        if (userRepo.findById(user_id) == null ) throw new UserNotFoundException(user_id);
        repository.deleteById(task_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

class UserTaskNotFoundException extends RuntimeException {
    UserTaskNotFoundException(Long userId, Long taskId) {
        super(String.format("Could not find task with user: %s and task : %s", userId, taskId));
    }
}

class TaskNotFoundException extends RuntimeException {
    TaskNotFoundException(Long id) {
        super("Could not find task " + id);
    }
}

@ControllerAdvice
class TaskNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String taskNotFoundHandler(TaskNotFoundException ex) {
        return ex.getMessage();
    }
}

@ControllerAdvice
class UserTaskNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(UserTaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userTaskNotFoundHandler(UserTaskNotFoundException ex) {
        return ex.getMessage();
    }
}