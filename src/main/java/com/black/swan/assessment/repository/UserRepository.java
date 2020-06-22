package com.black.swan.assessment.repository;

import com.black.swan.assessment.dto.user.OutputUserDto;
import com.black.swan.assessment.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT new com.black.swan.assessment.dto.user.OutputUserDto(u.id, u.username, u.firstName, u.lastName) FROM User u WHERE u.id = :userId")
    public Optional<OutputUserDto> findByUserId(Long userId);

    @Query("SELECT new com.black.swan.assessment.dto.user.OutputUserDto(u.id, u.username, u.firstName, u.lastName) FROM User u")
    public List<OutputUserDto> findAllUsers();

    @Query("SELECT u FROM User u where u.username = :username")
    public User findUserByUserName(String username);
}
