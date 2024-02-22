package pl.library.libraryonlinewebservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationSuccessHandlerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Authentication authentication;

    private MockHttpServletResponse response;

    @InjectMocks
    private JwtAuthenticationSuccessHandler successHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        response = new MockHttpServletResponse();
        when(authentication.getName()).thenReturn("user");
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        when(authentication
                .getAuthorities())
                .thenAnswer((Answer<List<GrantedAuthority>>)
                        invocation -> Collections.singletonList(new SimpleGrantedAuthority("USER")));
        when(jwtService.createSignedJWT(anyString(), anyList())).thenReturn("mockedJwtToken");
    }

    @Test
    void whenOnAuthenticationSuccess_thenWriteJwtToResponse() throws IOException {
        // When
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Then
        verify(jwtService).createSignedJWT(eq("user"), anyList());
        String responseBody = response.getContentAsString();
        assertTrue(responseBody.contains("mockedJwtToken"));

    }
}
