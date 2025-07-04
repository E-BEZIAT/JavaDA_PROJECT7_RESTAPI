package serviceTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.parameters.BidListParameter;
import com.nnk.springboot.domain.response.BidListDTO;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @Mock
    private BidListService bidListService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bidListService = new BidListService(bidListRepository);
    }

    @Test
    public void createBidListTest() {
        BidListParameter bidListParameter = new BidListParameter();
        bidListParameter.setAccount("testA");
        bidListParameter.setType("testT");
        bidListParameter.setBidQuantity(10.0);

        BidList savedBidList = new BidList();
        savedBidList.setAccount("testA");
        savedBidList.setType("testT");
        savedBidList.setBidQuantity(10.0);

        when(bidListRepository.save(any(BidList.class))).thenReturn(savedBidList);

        bidListService.createBidList(bidListParameter);

        verify(bidListRepository, times(1)).save(argThat(bidList ->
                bidList.getAccount().equals("testA") &&
                bidList.getType().equals("testT") &&
                bidList.getBidQuantity().equals(10.0)
        ));
    }

    @Test
    public void updateBidListTest() {
        BidList bidList = new BidList("testA", "testT", 10.0);

        BidListParameter bidListParameter = new BidListParameter();
        bidListParameter.setAccount("testAccount");
        bidListParameter.setType("testType");
        bidListParameter.setBidQuantity(15.0);

        Integer id = 1;

        when(bidListRepository.findById(id)).thenReturn(Optional.of(bidList));

        bidListService.updateBidList(bidListParameter, id);

        verify(bidListRepository, times(1)).findById(id);
        verify(bidListRepository, times(1)).save(bidList);
        assertEquals("testAccount", bidList.getAccount());
        assertEquals("testType", bidList.getType());
        assertEquals(15.0, bidList.getBidQuantity());
    }

    @Test
    public void deleteBidListTest() {
        BidList bidList = new BidList("testA", "testT", 10.0);
        Integer id = 1;

        when(bidListRepository.findById(id)).thenReturn(Optional.of(bidList));

        bidListService.deleteBidList(id);

        verify(bidListRepository, times(1)).findById(id);
        verify(bidListRepository, times(1)).delete(bidList);
    }

    @Test
    public void readBidListTest() {
        BidList bidList = new BidList("testA", "testT", 10.0);
        int id = 1;

        when(bidListRepository.findById(id)).thenReturn(Optional.of(bidList));

        BidListDTO result = bidListService.readBidList(id);

        assertNotNull(result);
        assertEquals("testA", result.getAccount());
        assertEquals("testT", result.getType());
        assertEquals(10.0, result.getBidQuantity());
        verify(bidListRepository, times(1)).findById(id);
    }
}
