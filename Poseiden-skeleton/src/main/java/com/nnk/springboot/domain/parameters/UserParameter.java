package com.nnk.springboot.domain.parameters;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserParameter {

    private String username;
    private String password;
    private String fullname;
    private String role;
}
