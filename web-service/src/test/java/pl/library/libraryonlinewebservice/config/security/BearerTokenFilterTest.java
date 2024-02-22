package pl.library.libraryonlinewebservice.config.security;

import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import java.text.ParseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BearerTokenFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private SecurityContextHolderStrategy securityContextHolderStrategy;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private BearerTokenFilter bearerTokenFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(securityContextHolderStrategy.getContext()).thenReturn(securityContext);
    }

    @Test
    void whenAuthorizationHeaderIsMissing_thenContinueFilterChain() throws Exception {
        // Given: An HTTP request without an Authorization header
        when(request.getHeader("Authorization")).thenReturn(null);

        // When: The filter processes the request
        bearerTokenFilter.doFilter(request, response, chain);

        // Then: The filter chain is continued without interacting with the JwtService
        verify(chain).doFilter(request, response);
        verifyNoInteractions(jwtService);
    }

    @Test
    void whenAuthorizationHeaderIsEmpty_thenContinueFilterChain() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn("");

        // When
        bearerTokenFilter.doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(request, response);
        verifyNoInteractions(jwtService);
    }


    @Test
    void whenJwtIsInvalid_thenHandleAuthenticationFailure() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidtoken");
        doThrow(new JwtAuthenticationException("Invalid JWT")).when(jwtService).verifySignature(any(SignedJWT.class));

        // When
        bearerTokenFilter.doFilter(request, response, chain);

        // Then
        verifyNoMoreInteractions(chain);
        // Assuming there's a mechanism to verify failureHandler.onAuthenticationFailure is invoked.
    }

}
