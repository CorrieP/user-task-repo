package com.black.swan.assessment.test;

import com.black.swan.assessment.AssessmentApplication;
import com.black.swan.assessment.dto.user.InputUserDto;
import com.black.swan.assessment.persistence.User;
import com.black.swan.assessment.repository.UserRepository;
import com.black.swan.assessment.test.util.BlackSwanPgSqlContainer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@RunWith(SpringRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssessmentApplication.class)
public class UserRepoIntegrationTests {

    private static final InputUserDto user1 = new InputUserDto("testuser1","test","user1");
    private static final InputUserDto user2 = new InputUserDto("testuser2","test","user2");
    private static final InputUserDto user3 = new InputUserDto("testuser3","test","user3");
    private static final InputUserDto user4 = new InputUserDto("testuser4","test","user4");

    @Autowired
    private UserRepository userRepository;
    @Test
    void migrate() {
        // migration starts automatically,
        // since Spring Boot runs the Flyway scripts on startup
    }

    @Test
    void saveUser1_Test(){

        System.out.println("Testing Save User1");
        var user = userRepository.save(user1.toUserConvert());
        assertUserPersisted(user);
    }

    @Test
    public void updateUser2(){
        var user = userRepository.save(user4.toUserConvert());
        assertUserPersisted(user);
        User tst = userRepository.findUserByUserName(user.username);
        tst.username = "userUser44";
        var savedUser = userRepository.save(tst);
        assertThat(savedUser.username).isEqualTo("userUser44");
    }

    @Test
    void deleteUser(){
        var user = userRepository.save(user3.toUserConvert());
        User tst = userRepository.findUserByUserName(user.username);
        userRepository.delete(tst);
        User deleted = userRepository.findUserByUserName(user.username);
        assertThat(deleted);
    }

    @Test
    void findAllUsers(){
        var users = userRepository.findAllUsers();
        assertThat(users).isNotNull();
    }

    @Test
    @Transactional
    void findUserById(){
        var user = userRepository.save(user2.toUserConvert());
        var saved = userRepository.findByUserId(user.id);
        assertThat(saved).isNotNull();
    }

    private void assertUserPersisted(User input) {
        User tst = userRepository.getOne(input.id);
        assertThat(tst).isNotNull();
    }
}
