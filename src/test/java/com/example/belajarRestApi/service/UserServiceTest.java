package com.example.belajarRestApi.service;

import com.example.belajarRestApi.model.User;
import com.example.belajarRestApi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setPassword("password");
        mockUser.setName("Test User");
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        // Menggunakan UserDetails untuk menerima hasil yang benar
        UserDetails userDetails = userService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals(mockUser.getUsername(), userDetails.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }


    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknownuser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("unknownuser"));
        verify(userRepository, times(1)).findByUsername("unknownuser");
    }

    @Test
    void testRegisterUser() {
        // Mock encode untuk mengembalikan password terenkripsi
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("password");
        newUser.setName("New User");

        User registeredUser = userService.registerUser(newUser);

        // Verifikasi hasil
        assertNotNull(registeredUser);
        assertEquals("encodedPassword", registeredUser.getPassword()); // Pastikan password sesuai hasil encode
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }
    @Test
    void testEncodeAndSaveUser() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("password");
        newUser.setName("New User");

        User savedUser = userService.encodeAndSaveUser(newUser);

        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }



    @Test
    void testExistsByUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        boolean exists = userService.existsByUsername("testuser");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("testuser");
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(mockUser));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        User user = userService.getUserById(1L);

        assertNotNull(user);
        assertEquals(mockUser.getId(), user.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User user = userService.getUserById(1L);

        assertNull(user);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User updatedDetails = new User();
        updatedDetails.setUsername("updatedUser");
        updatedDetails.setName("Updated User");
        updatedDetails.setPassword("newPassword");

        User updatedUser = userService.updateUser(1L, updatedDetails);

        assertNotNull(updatedUser);
        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("Updated User", updatedUser.getName());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
