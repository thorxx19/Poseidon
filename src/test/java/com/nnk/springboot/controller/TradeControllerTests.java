package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
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
public class TradeControllerTests {

    @Autowired
    private TradeController tradeController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TradeService tradeService;
    @MockBean
    private TradeRepository tradeRepository;

    @Test
    void getAllBidList(){

        List<Trade> bidListData = Arrays.asList(
                new Trade(),
                new Trade(),
                new Trade()
        );

        given(tradeRepository.findAll()).willReturn(bidListData);

        try {
            mockMvc.perform(get("/trade/list"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("trade/list"))
                    .andExpect(model().attributeExists("trade"))
                    .andExpect(model().attribute("trade", bidListData));

            verify(tradeService).home(any(Model.class));

        } catch (Exception e) {
            log.error("error :", e);
        }
    }
    @Test
    public void testAddTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidateWithError() throws Exception {


        BidList bid = new BidList();
        bid.setAccount("account");
        bid.setType("type");


        mockMvc.perform(post("/trade/validate").with(csrf())
                        .flashAttr("bid", bid))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

        verifyNoInteractions(tradeRepository);
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidateWithNoError() throws Exception {

        List<Trade> tradeList = new ArrayList<>();

        Trade trade = new Trade();
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(10d);

        tradeList.add(trade);

        given(tradeRepository.findAll()).willReturn(tradeList);

        mockMvc.perform(post("/trade/validate").with(csrf())
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(trade.getBuyQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeRepository, times(1)).findAll();
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowUpdateForm() throws Exception {

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(10d);

        given(tradeRepository.findById(1)).willReturn(Optional.of(trade));

        mockMvc.perform(get("/trade/update/{id}", trade.getTradeId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", hasProperty("tradeId", is(1))))
                .andExpect(model().attribute("trade", hasProperty("account", is("Account"))))
                .andExpect(model().attribute("trade", hasProperty("type", is("Type"))))
                .andExpect(model().attribute("trade", hasProperty("buyQuantity", is(10.0))));
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowUpdateFormWithInvalidId() {

        Integer invalidId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tradeController.showUpdateForm(invalidId, null);
        });

        String expectedMessage = "Invalid trade Id:" + invalidId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateTradeSuccess() throws Exception {


        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(10d);

        Trade trade2 = new Trade();
        trade2.setAccount("Account2");
        trade2.setType("Type2");
        trade2.setBuyQuantity(15d);

        when(tradeRepository.findById(trade.getTradeId())).thenReturn(Optional.of(trade));
        when(tradeRepository.save(trade2)).thenReturn(trade2);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/{id}", trade.getTradeId()).with(csrf())
                        .param("account", trade2.getAccount())
                        .param("type", trade2.getType())
                        .param("buyQuantity", String.valueOf(trade2.getBuyQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        assertNotEquals(trade2.getAccount(), trade.getAccount());
        assertNotEquals(trade2.getType(), trade.getType());
        assertNotEquals(trade2.getBuyQuantity(), trade.getBuyQuantity());
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateBidError() throws Exception {

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(10d);

        Trade trade2 = new Trade();
        trade2.setAccount("Account2");
        trade2.setType("Type2");


        when(tradeRepository.findById(trade.getTradeId())).thenReturn(Optional.of(trade));
        when(tradeRepository.save(trade2)).thenReturn(trade2);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/{id}", trade.getTradeId()).with(csrf())
                        .param("account", trade2.getAccount())
                        .param("type", trade2.getType()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));

        verifyNoInteractions(tradeRepository);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDeleteBidWithValidId() throws Exception {

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(10d);

        given(tradeRepository.findById(1)).willReturn(Optional.of(trade));

        mockMvc.perform(delete("/trade/delete/{id}", trade.getTradeId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testdeleteBidWithInvalidId() {

        Integer invalidId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tradeController.deleteTrade(invalidId, null);
        });

        String expectedMessage = "Invalid trade Id:" + invalidId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
