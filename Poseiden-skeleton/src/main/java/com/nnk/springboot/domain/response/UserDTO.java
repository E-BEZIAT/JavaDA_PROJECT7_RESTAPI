package com.nnk.springboot.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

    private final Integer id;
    private String username;
    private String password;
    private String fullname;
    private String role;

    public UserDTO(Integer id, String username, String password, String fullname, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }
}
