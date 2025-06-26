package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.parameters.UserParameter;
import com.nnk.springboot.domain.response.UserDTO;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(UserParameter userParameter) {
        Optional<User> optUser = userRepository.findByUsernameAndPassword(
                userParameter.getUsername(),
                userParameter.getPassword());

        if (optUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User(
                userParameter.getUsername(),
                userParameter.getPassword(),
                userParameter.getFullname(),
                userParameter.getRole()
        );

        userRepository.save(user);
    }

    public void updateUser(String oldUsername, UserParameter userParameter) {
        User user = userRepository.findByUsername(oldUsername);

        if (user == null) {
            throw new IllegalArgumentException("user not found");
        }

        String newUsername = userParameter.getUsername();
        if (newUsername != null && !newUsername.isBlank() && !newUsername.equals(oldUsername)) {

            if (userRepository.findByUsername(newUsername) != null) {
                throw new IllegalArgumentException("Ce nom d'utilisateur n'est pas disponible.");
            }

            user.setUsername(newUsername);
        }

        if (userParameter.getPassword() != null && !userParameter.getPassword().isBlank()) {
            user.setPassword(userParameter.getPassword());
        }

        if (userParameter.getFullname() != null && !userParameter.getFullname().isBlank()) {
            user.setFullname(userParameter.getFullname());
        }

        if (userParameter.getRole() != null && !userParameter.getRole().isBlank()) {
            user.setRole(userParameter.getRole());
        }

        userRepository.save(user);
    }

    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
    }

    public UserDTO readUser (String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("user not found");
        }

        return new UserDTO(
                user.getUsername(),
                user.getPassword(),
                user.getFullname(),
                user.getRole()
        );
    }
}
