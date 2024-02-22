package pl.library.libraryonlinewebservice.config.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.library.libraryonlinewebservice.domain.user.UserService;
import pl.library.libraryonlinewebservice.domain.user.dto.UserCredentialsDto;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void whenUserFound_thenCreateUserDetails() {
        // Given
        String email = "user@example.com";
        String password = "password";
        Set<String> roles = Set.of("USER");
        UserCredentialsDto foundUser = new UserCredentialsDto(email, password, roles);
        when(userService.findCredentialsByEmail(email)).thenReturn(Optional.of(foundUser));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Then
        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));

        verify(userService).findCredentialsByEmail(email);
    }
    @Test
    void whenUserNotFound_thenThrowUsernameNotFoundException() {
        // Given
        String email = "nonexistent@example.com";
        when(userService.findCredentialsByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));

        verify(userService).findCredentialsByEmail(email);
    }
}
