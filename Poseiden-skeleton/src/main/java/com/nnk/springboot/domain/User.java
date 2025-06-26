package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@ToString
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    @Column(length = 125)
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Column(length = 125)
    private String password;
    @NotBlank(message = "FullName is mandatory")
    @Column(length = 125)
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    @Column(length = 125)
    private String role;

    public User(String username, String password, String fullname, String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    public User() {}
}
