package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
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
public class RatingControllerTests {
    @Autowired
    private RatingController ratingController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RatingService ratingService;
    @MockBean
    private RatingRepository ratingRepository;


    @Test
    void getAllBidList(){

        List<Rating> ratingData = Arrays.asList(
                new Rating(),
                new Rating(),
                new Rating()
        );

        given(ratingRepository.findAll()).willReturn(ratingData);

        try {
            mockMvc.perform(get("/rating/list"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("rating/list"))
                    .andExpect(model().attributeExists("rating"))
                    .andExpect(model().attribute("rating", ratingData));

            verify(ratingService).home(any(Model.class));

        } catch (Exception e) {
            log.error("error :", e);
        }
    }
    @Test
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidateWithError() throws Exception {


        Rating rating = new Rating();
        rating.setMoodysRating("Moody Test");
        rating.setSandPRating("Sand Test");


        mockMvc.perform(post("/rating/validate").with(csrf())
                        .param("moodysRating", rating.getMoodysRating())
                        .param("sandPRating", rating.getSandPRating()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

        verifyNoInteractions(ratingRepository);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidateWithNoError() throws Exception {

        List<Rating> ratingList = new ArrayList<>();

        Rating rating = new Rating();
        rating.setMoodysRating("Moody Test");
        rating.setSandPRating("Sand Test");
        rating.setFitchRating("Fitch test");
        rating.setOrderNumber(10);

        ratingList.add(rating);

        given(ratingRepository.findAll()).willReturn(ratingList);

        mockMvc.perform(post("/rating/validate").with(csrf())
                        .param("moodysRating", rating.getMoodysRating())
                        .param("sandPRating", rating.getSandPRating())
                        .param("fitchRating", rating.getFitchRating())
                        .param("orderNumber", String.valueOf(rating.getOrderNumber())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowUpdateForm() throws Exception {

        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moody Test");
        rating.setSandPRating("Sand Test");
        rating.setFitchRating("Fitch test");
        rating.setOrderNumber(10);

        given(ratingRepository.findById(1)).willReturn(Optional.of(rating));

        mockMvc.perform(get("/rating/update/{id}", rating.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", hasProperty("id", is(1))))
                .andExpect(model().attribute("rating", hasProperty("moodysRating", is("Moody Test"))))
                .andExpect(model().attribute("rating", hasProperty("sandPRating", is("Sand Test"))))
                .andExpect(model().attribute("rating", hasProperty("fitchRating", is("Fitch test"))))
                .andExpect(model().attribute("rating", hasProperty("orderNumber", is(10))));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowUpdateFormWithInvalidId() {

        Integer invalidId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ratingController.showUpdateForm(invalidId, null);
        });

        String expectedMessage = "Invalid rating Id:" + invalidId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateBidSuccess() throws Exception {


        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moody Test");
        rating.setSandPRating("Sand Test");
        rating.setFitchRating("Fitch test");
        rating.setOrderNumber(10);

        Rating rating2 = new Rating();
        rating2.setMoodysRating("Moody Test2");
        rating2.setSandPRating("Sand Test2");
        rating2.setFitchRating("Fitch test2");
        rating2.setOrderNumber(15);

        when(ratingRepository.findById(rating.getId())).thenReturn(Optional.of(rating));
        when(ratingRepository.save(rating2)).thenReturn(rating2);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/{id}", rating.getId()).with(csrf())
                        .param("moodysRating", rating2.getMoodysRating())
                        .param("sandPRating", rating2.getSandPRating())
                        .param("fitchRating", rating2.getFitchRating())
                        .param("orderNumber", String.valueOf(rating2.getOrderNumber())))
                .andExpect(redirectedUrl("/rating/list"));

        assertNotEquals(rating2.getMoodysRating(), rating.getMoodysRating());
        assertNotEquals(rating2.getSandPRating(), rating.getSandPRating());
        assertNotEquals(rating2.getFitchRating(), rating.getFitchRating());
        assertNotEquals(rating2.getOrderNumber(), rating.getOrderNumber());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateRatingError() throws Exception {

        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moody Test");
        rating.setSandPRating("Sand Test");
        rating.setFitchRating("Fitch test");
        rating.setOrderNumber(10);

        Rating rating2 = new Rating();
        rating2.setMoodysRating("Moody Test2");
        rating2.setSandPRating("Sand Test2");
        rating2.setFitchRating("Fitch test2");


        when(ratingRepository.findById(rating.getId())).thenReturn(Optional.of(rating));
        when(ratingRepository.save(rating2)).thenReturn(rating2);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/{id}", rating.getId()).with(csrf())
                        .param("moodysRating", rating2.getMoodysRating())
                        .param("sandPRating", rating2.getSandPRating()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));

        verifyNoInteractions(ratingRepository);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDeleteBidWithValidId() throws Exception {

        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moody Test");
        rating.setSandPRating("Sand Test");
        rating.setFitchRating("Fitch test");
        rating.setOrderNumber(10);

        given(ratingRepository.findById(1)).willReturn(Optional.of(rating));

        mockMvc.perform(delete("/rating/delete/{id}", rating.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testdeleteBidWithInvalidId() {

        Integer invalidId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ratingController.deleteRating(invalidId, null);
        });

        String expectedMessage = "Invalid rating Id:" + invalidId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
