package com.black.swan.assessment.controller;

import com.black.swan.assessment.dto.user.InputUserDto;
import com.black.swan.assessment.dto.user.OutputUserDto;
import com.black.swan.assessment.dto.user.UpdateUserDto;
import com.black.swan.assessment.persistence.User;
import com.black.swan.assessment.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController()
public class UserController {
    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/user")
    List<OutputUserDto> all() {
        return repository.findAllUsers();
    }

    @PostMapping("/user")
    OutputUserDto newUser(@RequestBody InputUserDto newUser) {
        User user = newUser.toUserConvert();
        return repository.save(user).toUserOutputConvert();
    }

    @PutMapping("/user/{id}")
    OutputUserDto updateUser(@RequestBody UpdateUserDto updateUser, @PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
                    user.modified = OffsetDateTime.now();
                    user.firstName = updateUser.firstName;
                    user.lastName = updateUser.lastName;
                    return repository.save(user).toUserOutputConvert();
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/user/{id}")
    OutputUserDto findUserById(@PathVariable Long id) {
        return repository.findByUserId(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}

class UserNotFoundException extends RuntimeException {
    UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}

@ControllerAdvice
class UserNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }
}