package com.mvp.crudmicroservice.user.web.dto.user;

import com.mvp.crudmicroservice.user.domain.user.Role;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void getId() {
        Long id = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(id);
        assertEquals(id, userDto.getId());
    }

    @Test
    void getName() {
        String name = "Vera Gor";
        UserDto userDto = new UserDto();
        userDto.setName(name);
        assertEquals(name, userDto.getName());
    }

    @Test
    void getUsername() {
        String username = "veragor";
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        assertEquals(username, userDto.getUsername());
    }

    @Test
    void getPassword() {
        String password = "password123";
        UserDto userDto = new UserDto();
        userDto.setPassword(password);
        assertEquals(password, userDto.getPassword());
    }

    @Test
    void testGetRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_ADMIN);
        roles.add(Role.ROLE_USER);

        UserDto userDto = new UserDto();
        userDto.setRoles(roles);

        assertEquals(roles, userDto.getRoles());
    }

    @Test
    void setId() {
        Long id = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(id);
        assertEquals(id, userDto.getId());
    }

    @Test
    void setName() {
        String name = "Vera Gor";
        UserDto userDto = new UserDto();
        userDto.setName(name);
        assertEquals(name, userDto.getName());
    }

    @Test
    void setUsername() {
        String username = "veragor";
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        assertEquals(username, userDto.getUsername());
    }

    @Test
    void setPassword() {
        String password = "password123";
        UserDto userDto = new UserDto();
        userDto.setPassword(password);
        assertEquals(password, userDto.getPassword());
    }

    @Test
    void testRoles() {
        UserDto userDto = new UserDto();
        userDto.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
        assertEquals(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER), userDto.getRoles());
    }

    @Test
    void testEquals() {
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setUsername("veragor");

        UserDto userDto2 = new UserDto();
        userDto2.setId(1L);
        userDto2.setUsername("veragor");

        assertTrue(userDto1.equals(userDto2));
    }

    @Test
    void canEqual() {
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setUsername("veragor");

        UserDto userDto2 = new UserDto();
        userDto2.setId(1L);
        userDto2.setUsername("veragor");

        assertTrue(userDto1.canEqual(userDto2));
    }

    @Test
    void testHashCode() {
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setUsername("veragor");

        UserDto userDto2 = new UserDto();
        userDto2.setId(1L);
        userDto2.setUsername("veragor");

        assertEquals(userDto1.hashCode(), userDto2.hashCode());
    }

    @Test
    void testToString() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("veragor");

        String expected = "UserDto(id=1, name=null, username=veragor, password=null, roles=null)";
        assertEquals(expected, userDto.toString());
    }
}
