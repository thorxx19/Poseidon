package com.nnk.springboot.controller;



import com.nnk.springboot.domain.JwtUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.HomeService;
import com.nnk.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import java.security.Principal;
import java.util.*;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class HomeControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HomeService homeService;

    @Test
    public void testHomeRedirectsFormlogin() throws Exception {
        mockMvc.perform(formLogin().user("olivier").password("Eagusto8060#"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testHomeRedirectsOauth2() throws Exception {
        // Création d'un objet d'authentification OAuth2 pour un utilisateur ayant un rôle ADMIN
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "123456");
        attributes.put("email", "admin@example.com");
        attributes.put("name", "Admin User");
        attributes.put("picture", "https://example.com/picture.jpg");
        attributes.put("authorities", authorities);
        OAuth2User oauth2User = new DefaultOAuth2User(authorities, attributes, "sub");

        // Simulation de l'authentification OAuth2 dans la requête
        Authentication authentication = new OAuth2AuthenticationToken(oauth2User, authorities, "client-registration-id");
        mockMvc.perform(get("/login").with(SecurityMockMvcRequestPostProcessors.authentication(authentication)))
                .andExpect(status().isOk());
    }

}
