package com.black.swan.assessment.dto.user;

import com.black.swan.assessment.persistence.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InputUserDto{
    @JsonProperty("username") public String username;
    @JsonProperty("first_name") public String firstName;
    @JsonProperty("last_name") public String lastName;

    public User toUserConvert(){
        var user =  new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

}
