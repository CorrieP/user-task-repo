package com.black.swan.assessment.test;

import com.black.swan.assessment.AssessmentApplication;
import com.black.swan.assessment.dto.task.InputTaskDto;
import com.black.swan.assessment.dto.user.InputUserDto;
import com.black.swan.assessment.persistence.Task;
import com.black.swan.assessment.repository.TaskRepository;
import com.black.swan.assessment.repository.UserRepository;
import com.black.swan.assessment.test.util.BlackSwanPgSqlContainer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssessmentApplication.class)
public class TaskRepoIntegrationTests {

    private static final InputTaskDto task1 = new InputTaskDto("testtask1","Description 1", OffsetDateTime.now());
    private static final InputTaskDto task2 = new InputTaskDto("testtask1","Description 2", OffsetDateTime.now());

    private static final InputUserDto user1 = new InputUserDto("testuser1","test","user1");

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTask1_Test(){
        System.out.println("Testing Save Task1");
        var user = userRepository.save(user1.toUserConvert());
        var tsk = task1.toTaskConvert();
        tsk.user = user;
        var saved = taskRepository.save(tsk);
        assertTaskPersisted(saved);
    }


    private void assertTaskPersisted(Task input) {
        Task tst = taskRepository.getOne(input.id);
        assertThat(tst).isNotNull();
    }
}
