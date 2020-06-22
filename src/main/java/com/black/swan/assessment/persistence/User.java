package com.black.swan.assessment.persistence;

import com.black.swan.assessment.dto.user.OutputUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Table(schema = "public", name = "`user`")
@Entity
@SQLDelete(sql = "UPDATE \"user\" SET active = false WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "active = true")
public class User extends BaseEntity{

    @Id
    @SequenceGenerator(initialValue = 2001, name = "user_idgen", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_idgen")
    public Long id;

    @Column
    public String username;

    @Column
    public String firstName;

    @Column
    public String lastName;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    public final List<Task> tasks = null;

    public OutputUserDto toUserOutputConvert(){
        var userDto =  new OutputUserDto();
        BeanUtils.copyProperties(this, userDto);
        return userDto;
    }
}
