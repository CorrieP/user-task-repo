package com.black.swan.assessment.persistence;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.OffsetDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable{
    @CreatedDate
    @Column(nullable = false)
    public OffsetDateTime created = OffsetDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    public OffsetDateTime modified = OffsetDateTime.now();

    @Column(nullable = false)
    public Boolean active = true;
}
