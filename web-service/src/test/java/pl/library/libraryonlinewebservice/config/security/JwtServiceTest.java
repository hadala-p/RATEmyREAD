package pl.library.libraryonlinewebservice.config.security;

import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;
    private final String sharedKey = "12345-12345-12345-12345-12345-12345";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService(sharedKey);
    }

    @Test
    void createSignedJWT_ShouldGenerateToken() {
        // given
        String username = "user";
        List<String> authorities = List.of("ROLE_USER");

        // when
        String token = jwtService.createSignedJWT(username, authorities);

        // then
        assertNotNull(token);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void createAuthentication_ShouldReturnAuthenticationWithAuthorities() {
        // given
        String username = "user";
        List<String> authorities = List.of("ROLE_USER");
        String token = jwtService.createSignedJWT(username, authorities);

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            // when
            Authentication authentication = jwtService.createAuthentication(signedJWT);

            // then
            assertNotNull(authentication);
            assertEquals(username, authentication.getName());
            assertTrue(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        } catch (ParseException e) {
            fail("Parsing JWT failed", e);
        }
    }
}