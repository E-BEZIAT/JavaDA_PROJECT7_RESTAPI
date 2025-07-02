package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.parameters.RuleNameParameter;
import com.nnk.springboot.domain.response.RuleNameDTO;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public void createRuleName(RuleNameParameter ruleNameParameter) {

        RuleName ruleName = new RuleName(
                ruleNameParameter.getName(),
                ruleNameParameter.getDescription(),
                ruleNameParameter.getJson(),
                ruleNameParameter.getTemplate(),
                ruleNameParameter.getSqlStr(),
                ruleNameParameter.getSqlPart()
        );

        ruleNameRepository.save(ruleName);
    }

    public void updateRuleName(RuleNameParameter ruleNameParameter, int id) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleName not found"));

        ruleName.setName(ruleNameParameter.getName());
        ruleName.setDescription(ruleNameParameter.getDescription());
        ruleName.setJson(ruleNameParameter.getJson());
        ruleName.setTemplate(ruleNameParameter.getTemplate());
        ruleName.setSqlStr(ruleNameParameter.getSqlStr());
        ruleName.setSqlPart(ruleNameParameter.getSqlPart());
        ruleNameRepository.save(ruleName);
    }

    public void deleteRuleName(int id) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleName not found"));
        ruleNameRepository.delete(ruleName);
    }

    public RuleNameDTO readRuleName(int id) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleName not found"));

        return new RuleNameDTO(
                ruleName.getId(),
                ruleName.getName(),
                ruleName.getDescription(),
                ruleName.getJson(),
                ruleName.getTemplate(),
                ruleName.getSqlStr(),
                ruleName.getSqlPart()
        );
    }
}
