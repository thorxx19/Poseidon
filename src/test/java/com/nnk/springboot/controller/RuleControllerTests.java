package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class RuleControllerTests {

    @Autowired
    private RuleNameController ruleNameController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RuleNameService ruleNameService;
    @MockBean
    private RuleNameRepository ruleNameRepository;

    @Test
    void getAllBidList(){

        List<RuleName> ruleData = Arrays.asList(
                new RuleName(),
                new RuleName(),
                new RuleName()
        );

        given(ruleNameRepository.findAll()).willReturn(ruleData);

        try {
            mockMvc.perform(get("/ruleName/list"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("ruleName/list"))
                    .andExpect(model().attributeExists("ruleName"))
                    .andExpect(model().attribute("ruleName", ruleData));

            verify(ruleNameService).home(any(Model.class));

        } catch (Exception e) {
            log.error("error :", e);
        }
    }
    @Test
    public void testAddRuleForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidateWithError() throws Exception {


        RuleName ruleName = new RuleName();
        ruleName.setName("name Test");
        ruleName.setDescription("Description Test");
        ruleName.setJson("Json test");
        ruleName.setTemplate("Template Test");
        ruleName.setSqlStr("Sql Str test");



        mockMvc.perform(post("/ruleName/validate").with(csrf())
                        .flashAttr("ruleName", ruleName))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));

        verifyNoInteractions(ruleNameRepository);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidateWithNoError() throws Exception {

        List<RuleName> ruleData = new ArrayList<>();

        RuleName ruleName = new RuleName();
        ruleName.setName("name Test");
        ruleName.setDescription("Description Test");
        ruleName.setJson("Json test");
        ruleName.setTemplate("Template Test");
        ruleName.setSqlStr("Sql Str test");
        ruleName.setSqlPart("Sql Part Test");

        ruleData.add(ruleName);

        given(ruleNameRepository.findAll()).willReturn(ruleData);

        mockMvc.perform(post("/ruleName/validate").with(csrf())
                        .flashAttr("ruleName", ruleName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowUpdateForm() throws Exception {

        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("name Test");
        ruleName.setDescription("Description Test");
        ruleName.setJson("Json test");
        ruleName.setTemplate("Template Test");
        ruleName.setSqlStr("Sql Str test");
        ruleName.setSqlPart("Sql Part Test");

        given(ruleNameRepository.findById(1)).willReturn(Optional.of(ruleName));

        mockMvc.perform(get("/ruleName/update/{id}", ruleName.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", hasProperty("id", is(1))))
                .andExpect(model().attribute("ruleName", hasProperty("name", is("name Test"))))
                .andExpect(model().attribute("ruleName", hasProperty("description", is("Description Test"))))
                .andExpect(model().attribute("ruleName", hasProperty("json", is("Json test"))))
                .andExpect(model().attribute("ruleName", hasProperty("template", is("Template Test"))))
                .andExpect(model().attribute("ruleName", hasProperty("sqlStr", is("Sql Str test"))))
                .andExpect(model().attribute("ruleName", hasProperty("sqlPart", is("Sql Part Test"))));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowUpdateFormWithInvalidId() {

        Integer invalidId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ruleNameController.showUpdateForm(invalidId, null);
        });

        String expectedMessage = "Invalid ruleName Id:" + invalidId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateRuleSuccess() throws Exception {


        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("name Test");
        ruleName.setDescription("Description Test");
        ruleName.setJson("Json test");
        ruleName.setTemplate("Template Test");
        ruleName.setSqlStr("Sql Str test");
        ruleName.setSqlPart("Sql Part Test");

        RuleName ruleName2 = new RuleName();
        ruleName2.setName("name Test2");
        ruleName2.setDescription("Description Test2");
        ruleName2.setJson("Json test2");
        ruleName2.setTemplate("Template Test");
        ruleName2.setSqlStr("Sql Str test2");
        ruleName.setSqlPart("Sql Part Test2");

        when(ruleNameRepository.findById(ruleName.getId())).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(ruleName2)).thenReturn(ruleName2);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/{id}", ruleName.getId()).with(csrf())
                        .param("name", ruleName2.getName())
                        .param("description", ruleName2.getDescription())
                        .param("json", ruleName2.getJson())
                        .param("template", ruleName.getDescription())
                        .param("sqlStr", ruleName2.getDescription())
                        .param("sqlPart", ruleName2.getDescription()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        assertNotEquals(ruleName2.getName(), ruleName.getName());
        assertNotEquals(ruleName2.getDescription(), ruleName.getDescription());
        assertNotEquals(ruleName2.getJson(), ruleName.getJson());
        assertEquals(ruleName2.getTemplate(), ruleName.getTemplate());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateRuleError() throws Exception {

        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("name Test");
        ruleName.setDescription("Description Test");
        ruleName.setJson("Json test");
        ruleName.setTemplate("Template Test");
        ruleName.setSqlStr("Sql Str test");
        ruleName.setSqlPart("Sql Part Test");

        RuleName ruleName2 = new RuleName();
        ruleName2.setName("name Test2");
        ruleName2.setDescription("Description Test2");
        ruleName2.setJson("Json test2");
        ruleName2.setTemplate("Template Test");
        ruleName2.setSqlStr("Sql Str test2");

        when(ruleNameRepository.findById(ruleName.getId())).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(ruleName2)).thenReturn(ruleName2);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/{id}", ruleName.getId()).with(csrf())
                        .param("name", ruleName2.getName())
                        .param("description", ruleName2.getDescription())
                        .param("json", ruleName2.getJson())
                        .param("template", ruleName.getDescription())
                        .param("sqlStr", ruleName2.getDescription()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));

        verifyNoInteractions(ruleNameRepository);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDeleteRuleWithValidId() throws Exception {

        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("name Test");
        ruleName.setDescription("Description Test");
        ruleName.setJson("Json test");
        ruleName.setTemplate("Template Test");
        ruleName.setSqlStr("Sql Str test");
        ruleName.setSqlPart("Sql Part Test");

        given(ruleNameRepository.findById(1)).willReturn(Optional.of(ruleName));

        mockMvc.perform(delete("/ruleName/delete/{id}", ruleName.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testdeleteBidWithInvalidId() {

        Integer invalidId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ruleNameController.deleteRuleName(invalidId, null);
        });

        String expectedMessage = "Invalid ruleName Id:" + invalidId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
