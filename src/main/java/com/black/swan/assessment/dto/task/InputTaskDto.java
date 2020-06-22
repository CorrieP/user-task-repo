package com.black.swan.assessment.dto.task;

import com.black.swan.assessment.persistence.Task;
import com.black.swan.assessment.persistence.TaskStatus;
import com.black.swan.assessment.persistence.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InputTaskDto {
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("date_time")
    public OffsetDateTime dateTime;

    public Task toTaskConvert(){
        var task =  new Task();
        BeanUtils.copyProperties(this, task);
        task.status = TaskStatus.PENDING;
        return task;
    }
}
//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//, timezone = "+02:00"