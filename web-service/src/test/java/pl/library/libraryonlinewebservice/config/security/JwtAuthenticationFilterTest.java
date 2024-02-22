package pl.library.libraryonlinewebservice.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenRequestDoesNotMatchPath_thenContinueFilterChain() throws Exception {
        // Given
        when(request.getMethod()).thenReturn("GET");
        when(request.getServletPath()).thenReturn("/not/api/auth");

        // When
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(authenticationManager);
    }

    @Test
    void whenAuthenticationFailed_thenCallFailureHandler() throws Exception {
        // Given
        String requestBody = "{\"username\":\"user\",\"password\":\"password\"}";
        when(request.getMethod()).thenReturn("POST");
        when(request.getServletPath()).thenReturn("/api/auth");
        when(request.getInputStream())
                .thenReturn(
                        new DelegatingServletInputStream(
                                new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8))));

        when(authenticationManager.authenticate(any())).thenThrow(new AuthenticationException("Test Exception") {
        });

        // When
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Then
        verify(authenticationManager).authenticate(any());
    }

}
