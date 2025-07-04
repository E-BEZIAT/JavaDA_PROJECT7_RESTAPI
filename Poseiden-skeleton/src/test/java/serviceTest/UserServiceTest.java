package serviceTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.parameters.UserParameter;
import com.nnk.springboot.domain.response.UserDTO;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void createUserTest() {
        UserParameter userParameter = new UserParameter();
        userParameter.setUsername("username");
        userParameter.setPassword("password");
        userParameter.setFullname("fullname");
        userParameter.setRole("role");

        User savedUser = new User("username", "password", "fullname", "role");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        userService.createUser(userParameter);

        verify(userRepository, times(1)).save(argThat(user ->
                user.getUsername().equals("username") &&
                user.getPassword().equals("encodedPassword") &&
                user.getFullname().equals("fullname") &&
                user.getRole().equals("role")
        ));
    }

    @Test
    public void updateUserTest() {
        UserParameter userParameter = new UserParameter();
        userParameter.setUsername("newUsername");
        userParameter.setPassword("newPassword");
        userParameter.setFullname("newFullname");
        userParameter.setRole("newRole");

        User user = new User("username", "password", "fullname", "role");

        int id = 1;

        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.updateUser(id, userParameter);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
        assertEquals("newUsername", user.getUsername());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals("newFullname", user.getFullname());
        assertEquals("newRole", user.getRole());
    }

    @Test
    public void deleteUserTest() {
        User user = new User("username", "password", "fullname", "role");
        int id = 1;

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.deleteUser(id);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void readUserTest() {
        User user = new User("username", "password", "fullname", "role");
        int id = 1;

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDTO result = userService.readUser(id);

        assertNotNull(result);
        assertEquals("username", result.getUsername());
        assertEquals("password", result.getPassword());
        assertEquals("fullname", result.getFullname());
        assertEquals("role", result.getRole());
        verify(userRepository, times(1)).findById(id);
    }
}
