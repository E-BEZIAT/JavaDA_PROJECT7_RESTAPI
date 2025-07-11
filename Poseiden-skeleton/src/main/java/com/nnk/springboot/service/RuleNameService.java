package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.parameters.RuleNameParameter;
import com.nnk.springboot.domain.response.RuleNameDTO;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * Permet de créer un RuleName
     *
     * @param ruleNameParameter body à remplir lors de la création d'un RuleName
     */
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

    /**
     * Permet d'update un RuleName
     *
     * @param ruleNameParameter body à remplir lors de l'update d'un RuleName
     * @param id id du RuleName à update
     */
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

    /**
     * Permet de supprimer un RuleName
     *
     * @param id id du RuleName à supprimer
     */
    public void deleteRuleName(int id) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleName not found"));

        ruleNameRepository.delete(ruleName);
    }

    /**
     * Permet de récupérer et lire un RuleName
     *
     * @param id id du RuleName à lire
     * @return un objet DTO qui contient les données nécessaires à la vue ou à l’API
     */
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
