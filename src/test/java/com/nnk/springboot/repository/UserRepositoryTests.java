package com.nnk.springboot.repository;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserTest() {

        User user = new User();
        user.setUsername("Olivier");
        user.setPassword("Eabcdefghijq123#");
        user.setFullname("Olivier Froidefond");
        user.setRole("ADMIN");

        //Save
        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertTrue(user.getUsername().equals("Olivier"));

        //Update
        user.setFullname("Olivier Dupond");
        user = userRepository.save(user);
        assertTrue(user.getFullname().equals("Olivier Dupond"));

        //Find
        List<User> listUser = userRepository.findAll();
        assertTrue(listUser.size() > 0);

        //Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> userList = userRepository.findById(id);
        assertFalse(userList.isPresent());

    }
}
