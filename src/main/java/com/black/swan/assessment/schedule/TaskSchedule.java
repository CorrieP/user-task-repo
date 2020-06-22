package com.black.swan.assessment.schedule;

import com.black.swan.assessment.persistence.Task;
import com.black.swan.assessment.persistence.TaskStatus;
import com.black.swan.assessment.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Component
@Transactional
public class TaskSchedule {
    private final Logger log = LoggerFactory.getLogger(TaskSchedule.class);

    private final TaskRepository repository;

    public TaskSchedule(TaskRepository repository){
        this.repository = repository;
    }
    @Scheduled(cron = "0 0/5 * * * ?")
    public void processScheduledTasks(){
        log.info("------------ BEGIN TASK SCHEDULE ------------");
        var expired = repository.findPendingExpiredTasks(OffsetDateTime.now());
        for(Task task : expired) {
            task.modified = OffsetDateTime.now();
            task.status = TaskStatus.DONE;
            System.out.println(String.format("Task : %s has completed", task.name));
            repository.save(task);
        }
        log.info("------------ END TASK SCHEDULE ------------");
    }
}
