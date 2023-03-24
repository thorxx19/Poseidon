package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTests {

    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getAllBidList(){

        List<User> userData = Arrays.asList(
                new User(),
                new User(),
                new User()
        );

        given(userRepository.findAll()).willReturn(userData);

        try {
            mockMvc.perform(get("/user/list"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/list"))
                    .andExpect(model().attributeExists("users"))
                    .andExpect(model().attribute("users", userData));

            verify(userService).home(any(Model.class));

        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testValidateWithError() throws Exception {


        User user = new User();
        user.setUsername("Olivier");
        user.setPassword("Eabcdefghijq");
        user.setFullname("Olivier Froidefond");
        user.setRole("ADMIN");


        mockMvc.perform(post("/user/validate").with(csrf())
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));

        verifyNoInteractions(userRepository);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testValidateWithNoError() throws Exception {

        List<User> userData = new ArrayList<>();

        User user = new User();
        user.setUsername("Dupont");
        user.setPassword("Eabcdefghijq123#");
        user.setFullname("Dupont MARCEL");
        user.setRole("ADMIN");

        userData.add(user);

        given(userRepository.findAll()).willReturn(userData);

        mockMvc.perform(post("/user/validate").with(csrf())
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(redirectedUrl("/user/list"));

        verify(userRepository, times(1)).findAll();
    }
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowUpdateForm() throws Exception {

        User user = new User();
        user.setId(1);
        user.setUsername("Olivier");
        user.setPassword("Eabcdefghijq123#");
        user.setFullname("Olivier Froidefond");
        user.setRole("ADMIN");

        given(userRepository.findById(1)).willReturn(Optional.of(user));

        mockMvc.perform(get("/user/update/{id}", user.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", hasProperty("id", is(1))))
                .andExpect(model().attribute("user", hasProperty("username", is("Olivier"))))
                .andExpect(model().attribute("user", hasProperty("password", is(""))))
                .andExpect(model().attribute("user", hasProperty("fullname", is("Olivier Froidefond"))))
                .andExpect(model().attribute("user", hasProperty("role", is("ADMIN"))));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowUpdateFormWithInvalidId() {

        Integer invalidId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.showUpdateForm(invalidId, null);
        });

        String expectedMessage = "Invalid user Id:" + invalidId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testUpdateBidSuccess() throws Exception {

        User user = new User();
        user.setId(1);
        user.setUsername("Olivier");
        user.setPassword("Eabcdefghijq123#");
        user.setFullname("Olivier Froidefond");
        user.setRole("ADMIN");

        User user2 = new User();
        user2.setUsername("Jean");
        user2.setPassword("Eabcdefghijq456#");
        user2.setFullname("Jean Froidefond");
        user2.setRole("ADMIN");


        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user2)).thenReturn(user2);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/{id}", user.getId()).with(csrf())
                .param("username", user2.getUsername())
                .param("password", user2.getPassword())
                .param("fullname", user2.getFullname())
                .param("role", user2.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        assertNotEquals(user2.getUsername(), user.getUsername());
        assertNotEquals(user2.getPassword(), user.getPassword());
        assertNotEquals(user2.getFullname(), user.getFullname());
        assertEquals(user2.getRole(), user.getRole());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testUpdateBidError() throws Exception {

        User user = new User();
        user.setId(1);
        user.setUsername("Olivier");
        user.setPassword("Eabcdefghijq123#");
        user.setFullname("Olivier Froidefond");
        user.setRole("ADMIN");

        User user2 = new User();
        user2.setUsername("Jean");
        user2.setPassword("Eabcdefghijq456#");
        user2.setFullname("Jean Froidefond");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user2)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/{id}", user.getId()).with(csrf())
                        .param("username", user2.getUsername())
                        .param("password", user2.getPassword())
                        .param("fullname", user2.getFullname()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));

        verifyNoInteractions(userRepository);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteBidWithValidId() throws Exception {

        User user = new User();
        user.setId(1);
        user.setUsername("Olivier");
        user.setPassword("Eabcdefghijq123#");
        user.setFullname("Olivier Froidefond");
        user.setRole("ADMIN");

        given(userRepository.findById(1)).willReturn(Optional.of(user));

        mockMvc.perform(delete("/user/delete/{id}", user.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testdeleteBidWithInvalidId() {

        Integer invalidId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.deleteUser(invalidId, null);
        });

        String expectedMessage = "Invalid user Id:" + invalidId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
