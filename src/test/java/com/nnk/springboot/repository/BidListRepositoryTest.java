package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class BidListRepositoryTest {

    @Autowired
    BidListRepository bidListRepository;

    @Test
    public void bidListTest() {

        BidList bid = new BidList();

        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        // Save
        bid = bidListRepository.save(bid);
        assertNotNull(bid.getBidListId());
        assertEquals(bid.getBidQuantity(), 10d, 10d);

        // Update
        bid.setBidQuantity(20d);
        bid = bidListRepository.save(bid);
        assertEquals(bid.getBidQuantity(), 20d, 20d);

        // Find
        List<BidList> listResult = bidListRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = bid.getBidListId();
        bidListRepository.delete(bid);
        Optional<BidList> bidList = bidListRepository.findById(id);
        assertFalse(bidList.isPresent());
    }

}
