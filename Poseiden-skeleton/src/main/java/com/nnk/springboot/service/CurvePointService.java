package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.parameters.CurvePointParameter;
import com.nnk.springboot.domain.response.CurvePointDTO;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Permet de créer un CurvePoint
     *
     * @param curvePointParameter body à remplir lors de la création du CurvePoint
     */
    public void createCurvePoint(CurvePointParameter curvePointParameter) {
        CurvePoint curvePoint = new CurvePoint(
                curvePointParameter.getCurveId(),
                curvePointParameter.getAsOfDate(),
                curvePointParameter.getTerm(),
                curvePointParameter.getValue(),
                curvePointParameter.getCreationDate()
        );

        curvePointRepository.save(curvePoint);
    }

    /**
     * permet d'update un CurvePoint
     *
     * @param curvePointParameter body à remplir lors de l'update d'un CurvePoint
     * @param id id du CurvePoint à update
     */
    public void updateCurvePoint(CurvePointParameter curvePointParameter, int id) {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found"));

        curvePoint.setCurveId(curvePointParameter.getCurveId());
        curvePoint.setAsOfDate(curvePointParameter.getAsOfDate());
        curvePoint.setTerm(curvePointParameter.getTerm());
        curvePoint.setValue(curvePointParameter.getValue());
        curvePoint.setCreationDate(curvePointParameter.getCreationDate());

        curvePointRepository.save(curvePoint);
    }

    /**
     * Permet de supprimer un CurvePoint
     *
     * @param id id du CurvePoint à supprimer
     */
    public void deleteCurvePoint(int id) {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found"));

        curvePointRepository.delete(curvePoint);
    }

    /**
     * Permet de récupérer et lire un CurvePoint
     *
     * @param id id du CurvePoint à lire
     * @return un objet DTO qui contient les données nécessaires à la vue ou à l’API
     */
    public CurvePointDTO readCurvePoint(int id) {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found"));

        return new CurvePointDTO(
                curvePoint.getId(),
                curvePoint.getCurveId(),
                curvePoint.getAsOfDate(),
                curvePoint.getTerm(),
                curvePoint.getValue(),
                curvePoint.getCreationDate()
        );
    }
}
