package serviceTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.parameters.CurvePointParameter;
import com.nnk.springboot.domain.response.CurvePointDTO;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
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
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @Mock
    private CurvePointService curvePointService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        curvePointService = new CurvePointService(curvePointRepository);
    }

    @Test
    public void createCurvePointTest() {
        CurvePoint savedCurvePoint = new CurvePoint(2, 10d, 30d);

        CurvePointParameter curvePointParameter = new CurvePointParameter();
        curvePointParameter.setCurveId(2);
        curvePointParameter.setTerm(10d);
        curvePointParameter.setValue(30d);

        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(savedCurvePoint);

        curvePointService.createCurvePoint(curvePointParameter);

        verify(curvePointRepository, times(1)).save(argThat(curvePoint ->
                curvePoint.getCurveId() == 2 &&
                        curvePoint.getTerm().equals(10d) &&
                        curvePoint.getValue().equals(30d)
        ));
    }

    @Test
    public void updateCurvePointTest() {
        CurvePoint curvePoint = new CurvePoint(2, 10d, 30d);

        CurvePointParameter curvePointParameter = new CurvePointParameter();
        curvePointParameter.setCurveId(3);
        curvePointParameter.setTerm(20d);
        curvePointParameter.setValue(40d);

        Integer id = 1;

        when(curvePointRepository.findById(id)).thenReturn(Optional.of(curvePoint));

        curvePointService.updateCurvePoint(curvePointParameter, id);

        verify(curvePointRepository, times(1)).findById(id);
        verify(curvePointRepository, times(1)).save(curvePoint);
        assertEquals(3, curvePoint.getCurveId());
        assertEquals(20d, curvePoint.getTerm());
        assertEquals(40d, curvePoint.getValue());
    }

    @Test
    public void deleteCurvePointTest() {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint(2, 10d, 30d);

        when(curvePointRepository.findById(id)).thenReturn(Optional.of(curvePoint));

        curvePointService.deleteCurvePoint(id);

        verify(curvePointRepository, times(1)).findById(id);
        verify(curvePointRepository, times(1)).delete(curvePoint);
    }

    @Test
    public void readCurvePointTest() {
        CurvePoint curvePoint = new CurvePoint(2, 10d, 30d);
        int id = 1;

        when(curvePointRepository.findById(id)).thenReturn(Optional.of(curvePoint));

        CurvePointDTO result = curvePointService.readCurvePoint(id);

        assertNotNull(result);
        assertEquals(2, result.getCurveId());
        assertEquals(10d, result.getTerm());
        assertEquals(30d, result.getValue());
        verify(curvePointRepository, times(1)).findById(id);
    }
}