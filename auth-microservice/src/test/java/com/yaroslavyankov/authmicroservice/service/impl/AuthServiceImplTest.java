import com.yaroslavyankov.authmicroservice.domain.exception.AccessDeniedException;
import com.yaroslavyankov.authmicroservice.domain.exception.ResourceNotFoundException;
import com.yaroslavyankov.authmicroservice.domain.user.Role;
import com.yaroslavyankov.authmicroservice.service.props.LinkProperties;
import com.yaroslavyankov.authmicroservice.web.dto.UserDto;
import com.yaroslavyankov.authmicroservice.web.dto.auth.JwtRequest;
import com.yaroslavyankov.authmicroservice.web.dto.auth.JwtResponse;
import com.yaroslavyankov.authmicroservice.web.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import com.yaroslavyankov.authmicroservice.service.impl.AuthServiceImpl;
import com.yaroslavyankov.authmicroservice.web.dto.auth.RegisteredUser;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private LinkProperties linkProperties;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_Successful() {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("username");
        jwtRequest.setPassword("password");
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("username");
        userDto.setRoles(Collections.singleton(Role.ROLE_USER));

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(UserDto.class)))
                .thenReturn(new ResponseEntity<>(userDto, HttpStatus.OK));
        when(jwtTokenProvider.createAccessToken(anyLong(), anyString(), any()))
                .thenReturn("access_token");
        when(jwtTokenProvider.createRefreshToken(anyLong(), anyString()))
                .thenReturn("refresh_token");

        // Act
        JwtResponse jwtResponse = authService.login(jwtRequest);

        // Assert
        assertNotNull(jwtResponse);
        assertEquals(1L, jwtResponse.getId());
        assertEquals("username", jwtResponse.getUsername());
        assertEquals("access_token", jwtResponse.getAccessToken());
        assertEquals("refresh_token", jwtResponse.getRefreshToken());
    }

    @Test
    void login_AccessDenied() {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest();

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(UserDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> authService.login(jwtRequest));
    }


    @Test
    void refresh() {
        // Arrange
        String refreshToken = "refresh_token";
        JwtResponse expectedJwtResponse = new JwtResponse();
        expectedJwtResponse.setAccessToken("new_access_token");
        expectedJwtResponse.setRefreshToken("new_refresh_token");

        when(jwtTokenProvider.refreshUserTokens(refreshToken)).thenReturn(expectedJwtResponse);

        // Act
        JwtResponse jwtResponse = authService.refresh(refreshToken);

        // Assert
        assertNotNull(jwtResponse);
        assertEquals("new_access_token", jwtResponse.getAccessToken());
        assertEquals("new_refresh_token", jwtResponse.getRefreshToken());
    }
}
