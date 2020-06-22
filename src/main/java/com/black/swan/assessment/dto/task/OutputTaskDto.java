package com.black.swan.assessment.dto.task;

import com.black.swan.assessment.persistence.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OutputTaskDto {
    @JsonProperty("id")
    public Long id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("date_time")
    public OffsetDateTime dateTime;
    @JsonProperty("status")
    public TaskStatus status;
}

// timezone = "+02:00"
//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")