import com.yaroslavyankov.authmicroservice.config.ApplicationConfig;
import com.yaroslavyankov.authmicroservice.web.handlers.RestTemplateErrorHandler;
import com.yaroslavyankov.authmicroservice.web.security.JwtTokenFilter;
import com.yaroslavyankov.authmicroservice.web.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationConfigTest {

    private ApplicationConfig applicationConfig;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = mock(JwtTokenProvider.class);
        applicationContext = mock(ApplicationContext.class);
        applicationConfig = new ApplicationConfig(jwtTokenProvider, applicationContext);
    }

    @Test
    void restTemplate() {
        RestTemplate restTemplate = applicationConfig.restTemplate();
        assertNotNull(restTemplate);
        assertTrue(restTemplate.getErrorHandler() instanceof RestTemplateErrorHandler);
    }

    @Test
    void passwordEncoder() {
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
}




