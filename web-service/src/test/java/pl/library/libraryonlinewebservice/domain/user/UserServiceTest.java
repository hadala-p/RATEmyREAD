package pl.library.libraryonlinewebservice.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.library.libraryonlinewebservice.domain.user.dto.UserCredentialsDto;
import pl.library.libraryonlinewebservice.domain.user.dto.UserRegistrationDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindCredentialsByEmail_thenReturnUserCredentialsDto() {
        // Given
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        Optional<UserCredentialsDto> foundCredentials = userService.findCredentialsByEmail(email);

        // Then
        assertThat(foundCredentials).isPresent();
        assertThat(foundCredentials.get().getEmail()).isEqualTo(email);

    }

    @Test
    void whenRegisterUserWithDefaultRole_thenSaveUserWithEncodedPasswordAndDefaultRole() {
        // Given
        UserRegistrationDto userRegistration = new UserRegistrationDto();
        userRegistration.setEmail("newUser@example.com");
        userRegistration.setPassword("password123");
        UserRole defaultRole = new UserRole();
        defaultRole.setName("USER");
        when(userRoleRepository.findByName("USER")).thenReturn(Optional.of(defaultRole));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // When
        userService.registerUserWithDefaultRole(userRegistration);

        // Then
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("password123");
        verify(userRoleRepository).findByName("USER");
    }


    @Test
    void whenFindCredentialsByEmailAndUserDoesNotExist_thenReturnEmpty() {
        // Given
        String nonExistentEmail = "nonexistent@example.com";
        when(userRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        // When
        Optional<UserCredentialsDto> result = userService.findCredentialsByEmail(nonExistentEmail);

        // Then
        assertThat(result).isNotPresent();
    }
}
