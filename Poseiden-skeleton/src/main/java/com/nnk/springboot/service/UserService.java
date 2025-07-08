package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.parameters.UserParameter;
import com.nnk.springboot.domain.response.UserDTO;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Permet de créer un User
     *
     * @param userParameter body à remplir lors de la création d'un User
     */
    public void createUser(UserParameter userParameter) {
        Optional<User> optUser = userRepository.findByUsernameAndPassword(
                userParameter.getUsername(),
                userParameter.getPassword());

        if (optUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(userParameter.getPassword());

        User user = new User(
                userParameter.getUsername(),
                encodedPassword,
                userParameter.getFullname(),
                userParameter.getRole()
        );

        userRepository.save(user);
    }

    /**
     * Permet d'update un User
     *
     * @param id id du user à update
     * @param userParameter body à remplir lors de l'update d'un User
     */
    public void updateUser(int id, UserParameter userParameter) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        if (userParameter.getUsername() != null && !userParameter.getUsername().isBlank()) {
            user.setUsername(userParameter.getUsername());
        }

        if (userParameter.getPassword() != null && !userParameter.getPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(userParameter.getPassword());
            user.setPassword(encodedPassword);
        }

        if (userParameter.getFullname() != null && !userParameter.getFullname().isBlank()) {
            user.setFullname(userParameter.getFullname());
        }

        if (userParameter.getRole() != null && !userParameter.getRole().isBlank()) {
            user.setRole(userParameter.getRole());
        }

        userRepository.save(user);
    }

    /**
     * Permet de supprimer un User
     *
     * @param id id du User à supprimer
     */
    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
    }

    /**
     * Permet de récupérer et lire un User
     *
     * @param id id du User à lire
     * @return un objet DTO qui contient les données nécessaires à la vue ou à l’API
     */
    public UserDTO readUser (int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        if (user == null) {
            throw new IllegalArgumentException("user not found");
        }

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFullname(),
                user.getRole()
        );
    }
}
