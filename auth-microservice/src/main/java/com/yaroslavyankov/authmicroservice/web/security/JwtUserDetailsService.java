package com.yaroslavyankov.authmicroservice.web.security;

import com.yaroslavyankov.authmicroservice.service.props.LinkProperties;
import com.yaroslavyankov.authmicroservice.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    private final LinkProperties linkProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(linkProperties.getUserServiceLink())
                        .queryParam("username", username);
        ResponseEntity<UserDto> response
                = restTemplate.getForEntity(componentsBuilder.toUriString(), UserDto.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return JwtEntityFactory.create(response.getBody());
        }

        throw new UsernameNotFoundException("Пользователь с таким именем не найден.");
    }
}
