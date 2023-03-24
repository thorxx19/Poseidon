package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurveService;
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
public class CurvePointControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CurveController curveController;
    @Autowired
    private CurveService curveService;
    @MockBean
    private CurvePointRepository curvePointRepository;

    @Test
    void getAllcurveList(){

        List<CurvePoint> curvePointList = Arrays.asList(
                new CurvePoint(),
                new CurvePoint(),
                new CurvePoint()
        );

        given(curvePointRepository.findAll()).willReturn(curvePointList);

        try {
            mockMvc.perform(get("/curvePoint/list"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("curvePoint/list"))
                    .andExpect(model().attributeExists("curvePoint"))
                    .andExpect(model().attribute("curvePoint", curvePointList));

            verify(curveService).home(any(Model.class));

        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    public void testAddCurveForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidateWithError() throws Exception {


        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(12);
        curvePoint.setTerm(10d);
        mockMvc.perform(post("/curvePoint/validate").with(csrf())
                        .flashAttr("curvePoint", curvePoint))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

        verifyNoInteractions(curvePointRepository);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidateWithNoError() throws Exception {

        List<CurvePoint> curvePointList = new ArrayList<>();

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(12);
        curvePoint.setTerm(10d);
        curvePoint.setValue(10d);


        curvePointList.add(curvePoint);

        given(curvePointRepository.findAll()).willReturn(curvePointList);

        mockMvc.perform(post("/curvePoint/validate").with(csrf())
                        .param("curveId", String.valueOf(curvePoint.getCurveId()) )
                        .param("term", String.valueOf(curvePoint.getTerm()) )
                        .param("value", String.valueOf(curvePoint.getValue())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointRepository, times(1)).findAll();
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowUpdateForm() throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(12);
        curvePoint.setTerm(10d);
        curvePoint.setValue(10d);

        given(curvePointRepository.findById(1)).willReturn(Optional.of(curvePoint));

        mockMvc.perform(get("/curvePoint/update/{id}", curvePoint.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", hasProperty("id", is(1))))
                .andExpect(model().attribute("curvePoint", hasProperty("curveId", is(12))))
                .andExpect(model().attribute("curvePoint", hasProperty("term", is(10.0))))
                .andExpect(model().attribute("curvePoint", hasProperty("value", is(10.0))));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowUpdateFormWithInvalidId() {

        Integer invalidId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            curveController.showUpdateForm(invalidId, null);
        });

        String expectedMessage = "Invalid curvePointId:" + invalidId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateCurveSuccess() throws Exception {


        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(12);
        curvePoint.setTerm(10d);
        curvePoint.setValue(10d);

        CurvePoint curvePoint2 = new CurvePoint();
        curvePoint2.setCurveId(16);
        curvePoint2.setTerm(15d);
        curvePoint2.setValue(15d);

        when(curvePointRepository.findById(curvePoint.getId())).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(curvePoint2)).thenReturn(curvePoint2);

        mockMvc.perform(post("/curvePoint/update/{id}", curvePoint.getId()).with(csrf())
                        .param("curveId", String.valueOf(curvePoint2.getCurveId()))
                        .param("term", String.valueOf(curvePoint2.getTerm()) )
                        .param("value", String.valueOf(curvePoint2.getValue())))
                .andExpect(redirectedUrl("/curvePoint/list"));

        assertNotEquals(curvePoint2.getCurveId(), curvePoint.getCurveId());
        assertNotEquals(curvePoint2.getTerm(), curvePoint.getTerm());
        assertNotEquals(curvePoint2.getValue(), curvePoint.getValue());
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateCurveError() throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(12);
        curvePoint.setTerm(10d);
        curvePoint.setValue(10d);

        CurvePoint curvePoint2 = new CurvePoint();
        curvePoint2.setCurveId(12);
        curvePoint2.setTerm(10d);

        when(curvePointRepository.findById(curvePoint.getId())).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(curvePoint2)).thenReturn(curvePoint2);

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/{id}", curvePoint.getId()).with(csrf())
                        .param("curveId",  String.valueOf(curvePoint2.getCurveId()))
                        .param("term",  String.valueOf(curvePoint2.getTerm()))
                        .param("value", String.valueOf(curvePoint2.getValue())))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));

        verifyNoInteractions(curvePointRepository);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDeleteCurveWithValidId() throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(12);
        curvePoint.setTerm(10d);
        curvePoint.setValue(10d);

        given(curvePointRepository.findById(1)).willReturn(Optional.of(curvePoint));

        mockMvc.perform(delete("/curvePoint/delete/{id}", curvePoint.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testdeleteCurveWithInvalidId() {

        Integer invalidId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            curveController.deleteBid(invalidId, null);
        });

        String expectedMessage = "Invalid curvePoint Id:" + invalidId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
