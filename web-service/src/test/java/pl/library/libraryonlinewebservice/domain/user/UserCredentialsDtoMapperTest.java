package pl.library.libraryonlinewebservice.domain.user;

import org.junit.jupiter.api.Test;
import pl.library.libraryonlinewebservice.domain.user.User;
import pl.library.libraryonlinewebservice.domain.user.dto.UserCredentialsDto;
import pl.library.libraryonlinewebservice.domain.user.UserRole;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCredentialsDtoMapperTest {

    @Test
    void whenMapUserToUserCredentialsDto_thenPropertiesShouldMatch() {
        // Given
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password123");

        Set<UserRole> roles = new HashSet<>();
        UserRole roleUser = new UserRole();
        roleUser.setName("USER");
        roles.add(roleUser);

        UserRole roleAdmin = new UserRole();
        roleAdmin.setName("ADMIN");
        roles.add(roleAdmin);

        user.setRoles(roles);

        // When
        UserCredentialsDto dto = UserCredentialsDtoMapper.map(user);

        // Then
        assertThat(dto.getEmail()).isEqualTo(user.getEmail());
        assertThat(dto.getPassword()).isEqualTo(user.getPassword());
        assertThat(dto.getRoles()).hasSize(2);
        assertThat(dto.getRoles()).containsExactlyInAnyOrder("USER", "ADMIN");
    }
}
