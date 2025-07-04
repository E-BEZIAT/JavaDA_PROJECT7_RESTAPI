package serviceTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.parameters.TradeParameter;
import com.nnk.springboot.domain.response.TradeDTO;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
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
public class TradeServiceTest {

    @Mock
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tradeService = new TradeService(tradeRepository);
    }

    @Test
    public void createTradeTest() {
        TradeParameter tradeParameter = new TradeParameter();
        tradeParameter.setAccount("Trade Account");
        tradeParameter.setType("Type");

        Trade savedTrade = new Trade("Trade Account", "Type");

        when(tradeRepository.save(any(Trade.class))).thenReturn(savedTrade);

        tradeService.createTrade(tradeParameter);

        verify(tradeRepository, times(1)).save(argThat(trade ->
                trade.getAccount().equals("Trade Account") &&
                trade.getType().equals("Type")
        ));
    }

    @Test
    public void updateTradeTest() {
        Trade trade = new Trade("Trade Account", "Type");

        TradeParameter tradeParameter = new TradeParameter();
        tradeParameter.setAccount("Account");
        tradeParameter.setType("Trade Type");

        int id = 1;

        when(tradeRepository.findById(id)).thenReturn(Optional.of(trade));

        tradeService.updateTrade(tradeParameter, id);

        verify(tradeRepository, times(1)).findById(id);
        verify(tradeRepository, times(1)).save(trade);
        assertEquals("Account", trade.getAccount());
        assertEquals("Trade Type", trade.getType());
    }

    @Test
    public void deleteTradeTest() {
        Trade trade = new Trade("Trade Account", "Type");
        int id = 1;

        when(tradeRepository.findById(id)).thenReturn(Optional.of(trade));

        tradeService.deleteTrade(id);

        verify(tradeRepository, times(1)).findById(id);
        verify(tradeRepository, times(1)).delete(trade);
    }

    @Test
    public void readTradeTest() {
        Trade trade = new Trade("Trade Account", "Type");
        int id = 1;

        when(tradeRepository.findById(id)).thenReturn(Optional.of(trade));

        TradeDTO result = tradeService.readTrade(id);

        assertNotNull(result);
        assertEquals("Trade Account", result.getAccount());
        assertEquals("Type", result.getType());
        verify(tradeRepository, times(1)).findById(id);
    }
}
