package com.cherry.erp.modules.projet.controller.Mapper;

import com.cherry.erp.modules.projet.model.dto.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public  class TRSByProjectMapper {

    public TRSByProjectDTO toTRSByProjectDTO(Object[] result) {

            Long qteTotaleSum = ((Number) result[0]).longValue();
            Long qteNCSum = ((Number) result[1]).longValue();
            Long qteRebutSum = ((Number) result[2]).longValue();
            Long tempsReelSum = ((Number) result[3]).longValue();
            Long tempsArretSum = ((Number) result[4]).longValue();
            Long theoreticalQuantity = ((Number) result[5]).longValue();
            Double tauxQualite = ((Number) result[6]).doubleValue();
            Double tauxPerformance = ((Number) result[7]).doubleValue();
            Double tauxDisponibilite = ((Number) result[8]).doubleValue();
            Double TRS = ((Number) result[9]).doubleValue();

            return new TRSByProjectDTO(qteTotaleSum, qteNCSum, qteRebutSum,tempsReelSum,
                    tempsArretSum,theoreticalQuantity,tauxQualite,tauxPerformance,tauxDisponibilite,TRS);
    }

    public List<TRSByProjectDTO> toTRSByProjectDTOList(List<Object[]> results) {

            List<TRSByProjectDTO> dtos = new ArrayList<>();
            for (Object[] result : results) {
                dtos.add(toTRSByProjectDTO(result));
            }
            return dtos;

    }

    public TRSBYPhaseDTO toTRSBYPhaseDTO(Object[] result) {

            String phase = (String) result[0];
            Long qteTotaleSum = ((Number) result[1]).longValue();
            Long qteNCSum = ((Number) result[2]).longValue();
            Long qteRebutSum = ((Number) result[3]).longValue();
            Long tempsReelSum = ((Number) result[4]).longValue();
            Long tempsArretSum = ((Number) result[5]).longValue();
            Long theoreticalQuantity = ((Number) result[6]).longValue();
            Double tauxQualite = ((Number) result[7]).doubleValue();
            Double tauxPerformance = ((Number) result[8]).doubleValue();
            Double tauxDisponibilite = ((Number) result[9]).doubleValue();
            Double TRS = ((Number) result[10]).doubleValue();

            return new TRSBYPhaseDTO(phase, qteTotaleSum, qteNCSum, qteRebutSum,tempsReelSum,
                    tempsArretSum,theoreticalQuantity,tauxQualite,tauxPerformance,tauxDisponibilite,TRS);

    }
    public List<TRSBYPhaseDTO> toTRSByPhaseDTOList(List<Object[]> results) {
            List<TRSBYPhaseDTO> dtos = new ArrayList<>();
            for (Object[] result : results) {
                dtos.add(toTRSBYPhaseDTO(result));
            }
            return dtos;
    }


    public TRSBYEmpDTO toTRSBYEmpDTO(Object[] result) {
            Integer employeeId = ((Number) result[0]).intValue();
            String employeeNumber = (String) result[1];
            String firstName = (String) result[2];
            String lastName = (String) result[3];
            Double tauxQualite = ((Number) result[4]).doubleValue();
            Double tauxPerformance = ((Number) result[5]).doubleValue();
            Double tauxDisponibilite = ((Number) result[6]).doubleValue();
            Double TRS = ((Number) result[7]).doubleValue();

            return new TRSBYEmpDTO(employeeId, employeeNumber, firstName, lastName, tauxQualite,tauxPerformance,tauxDisponibilite,TRS);
    }

    public List<TRSBYEmpDTO> toTRSByEmpDTOList(List<Object[]> results) {
            List<TRSBYEmpDTO> dtos = new ArrayList<>();
            for (Object[] result : results) {
                dtos.add(toTRSBYEmpDTO(result));
            }
            return dtos;
    }

    public TRSBYEquipDTO toTRSBYEquipDTO(Object[] result) {

            String equipment = (String) result[0];
            Double tauxQualite = ((Number) result[1]).doubleValue();
            Double tauxPerformance = ((Number) result[2]).doubleValue();
            Double tauxDisponibilite = ((Number) result[3]).doubleValue();
            Double TRS = ((Number) result[4]).doubleValue();

            return new TRSBYEquipDTO(equipment, tauxQualite,tauxPerformance,tauxDisponibilite,TRS);
    }
    public List<TRSBYEquipDTO> toTRSByEquipDTOList(List<Object[]> results) {
            List<TRSBYEquipDTO> dtos = new ArrayList<>();
            for (Object[] result : results) {
                dtos.add(toTRSBYEquipDTO(result));
            }
            return dtos;

    }

    public TRSBYLigneDTO toTRSBYLigneDTO(Object[] result) {

            Long ligneProductionId = ((Number) result[0]).longValue();
            String name = (String) result[1];
            String reference = (String) result[2];
            Double tauxQualite = ((Number) result[3]).doubleValue();
            Double tauxPerformance = ((Number) result[4]).doubleValue();
            Double tauxDisponibilite = ((Number) result[5]).doubleValue();
            Double TRS = ((Number) result[6]).doubleValue();

            return new TRSBYLigneDTO(ligneProductionId, name, reference,
                    tauxQualite,tauxPerformance,tauxDisponibilite,TRS);
    }
    public List<TRSBYLigneDTO> toTRSByLigneDTOList(List<Object[]> results) {
            List<TRSBYLigneDTO> dtos = new ArrayList<>();
            for (Object[] result : results) {
                dtos.add(toTRSBYLigneDTO(result));
            }
            return dtos;

    }

    public StaticOFDTO toStaticOFDTO(Object[] result) {

            String phase = (String) result[0];
            Long quantite = ((Number) result[1]).longValue();
            Long qteTotaleSum = ((Number) result[2]).longValue();
            Long qtencSum = ((Number) result[3]).longValue();
            Double qteRebutSum = ((Number) result[4]).doubleValue();
            Double tempsReel = ((Number) result[5]).doubleValue();
            Double tempsEstime = ((Number) result[6]).doubleValue();

            return new StaticOFDTO(phase, quantite, qteTotaleSum,
                    qtencSum,qteRebutSum,tempsReel,tempsEstime);
    }
    public List<StaticOFDTO> toStaticOFDTOList(List<Object[]> results) {
            List<StaticOFDTO> dtos = new ArrayList<>();
            for (Object[] result : results) {
                dtos.add(toStaticOFDTO(result));
            }
            return dtos;
    }

    public QteProduiteGammeEmpDTO toQteProduiteOFDTO(Object[] result) {

        String phase = (String) result[0];
        Long employeeId = ((Number) result[1]).longValue();
        String employeeNumber = (String) result[2];
        String firstName = (String) result[3];
        String lastName = (String) result[4];
        Long qteTotaleSum = ((Number) result[5]).longValue();

        return new QteProduiteGammeEmpDTO(phase, employeeId,
                employeeNumber,firstName,lastName,qteTotaleSum);
    }
    public List<QteProduiteGammeEmpDTO> toQteProduiteList(List<Object[]> results) {
        List<QteProduiteGammeEmpDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            dtos.add(toQteProduiteOFDTO(result));
        }
        return dtos;
    }



}
