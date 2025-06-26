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

    public void deleteCurvePoint(int id) {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found"));

        curvePointRepository.delete(curvePoint);
    }

    public CurvePointDTO readRating(int id) {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found"));

        return new CurvePointDTO(
                curvePoint.getCurveId(),
                curvePoint.getAsOfDate(),
                curvePoint.getTerm(),
                curvePoint.getValue(),
                curvePoint.getCreationDate()
        );
    }
}
