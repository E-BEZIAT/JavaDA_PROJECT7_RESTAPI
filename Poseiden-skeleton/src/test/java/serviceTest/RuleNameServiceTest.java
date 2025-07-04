package serviceTest;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.parameters.RuleNameParameter;
import com.nnk.springboot.domain.response.RuleNameDTO;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ruleNameService = new RuleNameService(ruleNameRepository);
    }

    @Test
    public void createRuleNameTest() {
        RuleNameParameter ruleNameParameter = new RuleNameParameter();
        ruleNameParameter.setName("RuleName");
        ruleNameParameter.setDescription("Description");
        ruleNameParameter.setJson("Json");
        ruleNameParameter.setTemplate("Template");
        ruleNameParameter.setSqlStr("SqlStr");
        ruleNameParameter.setSqlPart("SqlPart");

        RuleName savedRuleName = new RuleName("RuleName", "Description", "Json", "Template", "SqlStr", "SqlPart");

        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(savedRuleName);

        ruleNameService.createRuleName(ruleNameParameter);

        verify(ruleNameRepository, times(1)).save(argThat(ruleName ->
                ruleName.getName().equals("RuleName") &&
                ruleName.getDescription().equals("Description") &&
                ruleName.getJson().equals("Json") &&
                ruleName.getTemplate().equals("Template") &&
                ruleName.getSqlStr().equals("SqlStr") &&
                ruleName.getSqlPart().equals("SqlPart")
        ));
    }

    @Test
    public void updateRuleNameTest() {
        RuleName ruleName = new RuleName("RuleName", "Description", "Json", "Template", "SqlStr", "SqlPart");

        RuleNameParameter ruleNameParameter = new RuleNameParameter();
        ruleNameParameter.setName("Name");
        ruleNameParameter.setDescription("Desc");
        ruleNameParameter.setJson("Js");
        ruleNameParameter.setTemplate("Temp");
        ruleNameParameter.setSqlStr("Str");
        ruleNameParameter.setSqlPart("Part");

        int id = 1;

        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(ruleName));

        ruleNameService.updateRuleName(ruleNameParameter, id);

        verify(ruleNameRepository, times(1)).findById(id);
        verify(ruleNameRepository, times(1)).save(ruleName);
        assertEquals("Name", ruleName.getName());
        assertEquals("Desc", ruleName.getDescription());
        assertEquals("Js", ruleName.getJson());
        assertEquals("Temp", ruleName.getTemplate());
        assertEquals("Str", ruleName.getSqlStr());
        assertEquals("Part", ruleName.getSqlPart());
    }

    @Test
    public void deleteRuleNameTest() {
        RuleName ruleName = new RuleName("RuleName", "Description", "Json", "Template", "SqlStr", "SqlPart");
        int id = 1;

        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(ruleName));

        ruleNameService.deleteRuleName(id);

        verify(ruleNameRepository, times(1)).findById(id);
        verify(ruleNameRepository, times(1)).delete(ruleName);
    }

    @Test
    public void readRuleNameTest() {
        RuleName ruleName = new RuleName("RuleName", "Description", "Json", "Template", "SqlStr", "SqlPart");
        int id = 1;

        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(ruleName));

        RuleNameDTO result = ruleNameService.readRuleName(id);

        assertNotNull(result);
        assertEquals("RuleName", result.getName());
        assertEquals("Description", result.getDescription());
        assertEquals("Json", result.getJson());
        assertEquals("Template", result.getTemplate());
        assertEquals("SqlStr", result.getSqlStr());
        assertEquals("SqlPart", result.getSqlPart());
        verify(ruleNameRepository, times(1)).findById(id);
    }
}
