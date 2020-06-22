package com.black.swan.assessment.persistence;

import com.black.swan.assessment.dto.task.OutputTaskDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper=true)
@Table(schema = "public", name = "task")
@Entity
@SQLDelete(sql = "UPDATE task SET active = false WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "active = true")
public class Task extends BaseEntity {
    @Id
    @SequenceGenerator(initialValue = 5001, name = "task_idgen", sequenceName = "task_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_idgen")
    public Long id;

    @Column
    public String name;

    @Column
    public String description;

    @ManyToOne(fetch = FetchType.LAZY)
    public User user;

    @Column
    public OffsetDateTime dateTime;

    @Enumerated(EnumType.STRING)
    public TaskStatus status;

    public OutputTaskDto toTaskOutputConvert(){
        var taskDto =  new OutputTaskDto();
        BeanUtils.copyProperties(this, taskDto);
        return taskDto;
    }
}

