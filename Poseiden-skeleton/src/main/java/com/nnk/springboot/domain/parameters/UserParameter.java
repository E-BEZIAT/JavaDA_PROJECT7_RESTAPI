package com.nnk.springboot.domain.parameters;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserParameter {

    private int id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Full name is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;
}
