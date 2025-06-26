package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.parameters.RuleNameParamater;
import com.nnk.springboot.domain.response.RuleNameDTO;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public void createRuleName(RuleNameParamater ruleNameParamater) {

        RuleName ruleName = new RuleName(
                ruleNameParamater.getName(),
                ruleNameParamater.getDescription(),
                ruleNameParamater.getJson(),
                ruleNameParamater.getTemplate(),
                ruleNameParamater.getSqlStr(),
                ruleNameParamater.getSqlPart()
        );

        ruleNameRepository.save(ruleName);
    }

    public void updateRuleName(RuleNameParamater ruleNameParamater, int id) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleName not found"));

        ruleName.setName(ruleNameParamater.getName());
        ruleName.setDescription(ruleNameParamater.getDescription());
        ruleName.setJson(ruleNameParamater.getJson());
        ruleName.setTemplate(ruleNameParamater.getTemplate());
        ruleName.setSqlStr(ruleNameParamater.getSqlStr());
        ruleName.setSqlPart(ruleNameParamater.getSqlPart());
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
                ruleName.getName(),
                ruleName.getDescription(),
                ruleName.getJson(),
                ruleName.getTemplate(),
                ruleName.getSqlStr(),
                ruleName.getSqlPart()
        );
    }
}
