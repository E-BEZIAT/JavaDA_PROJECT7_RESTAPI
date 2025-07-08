package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class userDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public userDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Donne le rôle "USER" ou "ADMIN" lors de la connexion au site, ce qui permet de pouvoir naviguer dessus
     *
     * @param username le nom d'utilisateur utilisé pour se connecter
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User profile = userRepository.findByUsername(username);
        if (profile == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(profile.getUsername())
                .password(profile.getPassword())
                .roles(profile.getRole())
                .build();
    }
}
