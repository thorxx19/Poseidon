package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
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
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class BidListControllerTests {

	@Autowired
	private BidListController bidListController;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private BidListService bidListService;
	@MockBean
	private BidListRepository bidListRepository;


	@Test
	void getAllBidList(){

		List<BidList> bidListData = Arrays.asList(
				new BidList(),
				new BidList(),
				new BidList()
		);

		given(bidListRepository.findAll()).willReturn(bidListData);

		try {
			mockMvc.perform(get("/bidList/list"))
					.andExpect(status().isOk())
					.andExpect(view().name("bidList/list"))
					.andExpect(model().attributeExists("bidList"))
					.andExpect(model().attribute("bidList", bidListData));

			verify(bidListService).home(any(Model.class));

		} catch (Exception e) {
			log.error("error :", e);
		}
	}
	@Test
	public void testAddBidForm() throws Exception {
		mockMvc.perform(get("/bidList/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/add"));
	}
	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testValidateWithError() throws Exception {


		BidList bid = new BidList();
		bid.setAccount("account");
		bid.setType("type");


		mockMvc.perform(post("/bidList/validate").with(csrf())
						.flashAttr("bid", bid))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/add"));

		verifyNoInteractions(bidListRepository);
	}
	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testValidateWithNoError() throws Exception {

		List<BidList> bidListData = new ArrayList<>();

		BidList bid = new BidList();
		bid.setAccount("account");
		bid.setType("type");
		bid.setBidQuantity(15d);

		bidListData.add(bid);

		given(bidListRepository.findAll()).willReturn(bidListData);

		mockMvc.perform(post("/bidList/validate").with(csrf())
						.param("account", bid.getAccount())
						.param("type",bid.getType())
						.param("bidQuantity", String.valueOf(bid.getBidQuantity())))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/bidList/list"))
				.andExpect(redirectedUrl("/bidList/list"));

		verify(bidListRepository, times(1)).findAll();
	}

	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testShowUpdateForm() throws Exception {

		BidList bidList = new BidList();
		bidList.setBidListId(1);
		bidList.setAccount("account");
		bidList.setType("type");
		bidList.setBidQuantity(15d);

		given(bidListRepository.findById(1)).willReturn(Optional.of(bidList));

		mockMvc.perform(get("/bidList/update/{id}", bidList.getBidListId()).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/update"))
				.andExpect(model().attribute("bidList", hasProperty("bidListId", is(1))))
				.andExpect(model().attribute("bidList", hasProperty("account", is("account"))))
				.andExpect(model().attribute("bidList", hasProperty("type", is("type"))))
				.andExpect(model().attribute("bidList", hasProperty("bidQuantity", is(15.0))));
	}

	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testShowUpdateFormWithInvalidId() {

		Integer invalidId = -1;

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			bidListController.showUpdateForm(invalidId, null);
		});

		String expectedMessage = "Invalid bidList Id:" + invalidId;
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testUpdateBidSuccess() throws Exception {


		BidList existingBidList = new BidList();
		existingBidList.setBidListId(1);
		existingBidList.setAccount("account1");
		existingBidList.setType("type1");
		existingBidList.setBidQuantity(100);

		BidList modifiedBidList = new BidList();
		modifiedBidList.setAccount("modifiedAccount1");
		modifiedBidList.setType("modifiedType1");
		modifiedBidList.setBidQuantity(200);

		when(bidListRepository.findById(existingBidList.getBidListId())).thenReturn(Optional.of(existingBidList));
		when(bidListRepository.save(modifiedBidList)).thenReturn(modifiedBidList);

		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/{id}", existingBidList.getBidListId()).with(csrf())
						.param("account", modifiedBidList.getAccount())
						.param("type", modifiedBidList.getType())
						.param("bidQuantity", String.valueOf(modifiedBidList.getBidQuantity())))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bidList/list"));

		assertNotEquals(modifiedBidList.getAccount(), existingBidList.getAccount());
		assertNotEquals(modifiedBidList.getType(), existingBidList.getType());
		assertNotEquals(modifiedBidList.getBidQuantity(), existingBidList.getBidQuantity());
	}
	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testUpdateBidError() throws Exception {

		BidList existingBidList = new BidList();
		existingBidList.setBidListId(1);
		existingBidList.setAccount("account1");
		existingBidList.setType("type1");

		BidList modifiedBidList = new BidList();
		modifiedBidList.setAccount("modifiedAccount1");
		modifiedBidList.setType("modifiedType1");

		when(bidListRepository.findById(existingBidList.getBidListId())).thenReturn(Optional.of(existingBidList));
		when(bidListRepository.save(modifiedBidList)).thenReturn(modifiedBidList);

		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/{id}", existingBidList.getBidListId()).with(csrf())
						.param("account", modifiedBidList.getAccount())
						.param("type", modifiedBidList.getType())
						.param("bidQuantity", String.valueOf(modifiedBidList.getBidQuantity())))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/update"));

		verifyNoInteractions(bidListRepository);
	}

	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testDeleteBidWithValidId() throws Exception {

		BidList bidList = new BidList();
		bidList.setBidListId(1);
		bidList.setAccount("account");
		bidList.setType("type");
		bidList.setBidQuantity(15d);

		given(bidListRepository.findById(1)).willReturn(Optional.of(bidList));

		mockMvc.perform(delete("/bidList/delete/{id}", bidList.getBidListId()).with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bidList/list"));
	}


	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testdeleteBidWithInvalidId() {

		Integer invalidId = -1;

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			bidListController.deleteBid(invalidId, null);
		});

		String expectedMessage = "Invalid bidList Id:" + invalidId;
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}


}
