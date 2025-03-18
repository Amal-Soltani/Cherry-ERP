package com.cherry.erp.modules.projet.service.impl;

import com.cherry.erp.common.utils.ReferenceConstant;
import com.cherry.erp.common.utils.SpiUtils;

import com.cherry.erp.modules.projet.model.enums.StatutProjetEnum;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.projet.service.ProjetBusiness;
import com.cherry.erp.modules.projet.repository.ProjetRepository;
import com.cherry.erp.modules.projet.service.TacheBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@AllArgsConstructor

public class ProjetBusinessImpl implements ProjetBusiness{

    private final ProjetRepository projetRepository;
    private final TacheBusiness tacheBusiness;
    private final SpiUtils spiUtils;

    @Override
    public String generateReference() {
        Integer nextRefNumber = projetRepository.getNextReferenceNumber(spiUtils.findCurrentCompany().getId());
        // Format the reference with the prefix and zero padding
        String reference = ReferenceConstant.PROJECT_REFERENCE_PREFIX
                + String.format(ReferenceConstant.PROJECT_REFRENCE_FORMAT,nextRefNumber );

        return reference;
    }

    @Override
    public Page<Projects> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return projetRepository.findByCompany(spiUtils.findCurrentCompany().getId(),pageParam);
    }

    @Override
    public Page<Projects> findByPageAndStatus( Integer page, Integer pageSize, String status) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        StatutProjetEnum statutEnum = StatutProjetEnum.valueOf(status.toLowerCase());
        return projetRepository.findByPageAndStatus(statutEnum,spiUtils.findCurrentCompany().getId(),pageParam);
    }

    @Override
    public Projects add(Projects projet) {
        if (projet.getId() != null && projet.getId() != 0) {
            Projects oldProjet = FindById(projet.getId());
            spiUtils.setOldValues(projet, oldProjet);
            projet.getCreationDate().getYear();
        }
        if (projet.getId() == null || projet.getId() == 0) {
            projet.setCompany(spiUtils.findCurrentCompany());
            projet.setReference(generateReference());
        }
        if (projet.getCompany() == null) {
            projet.setCompany(spiUtils.findCurrentCompany());
        }

        spiUtils.setModifiedByAndCreatedByFields(projet);
        projet.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());


        return projetRepository.save(projet);
    }


    @Override
    public Projects FindById(Integer idProjet) {
        return projetRepository.findByIdAndCompanyId(idProjet,spiUtils.findCurrentCompany().getId()).orElse(null);

    }

    @Override
    public List<Projects> FindAll() {
        return projetRepository.findByCompanyIdByOrderByModificationDateDesc(spiUtils.findCurrentCompany().getId()) ;
    }

    @Override
    public void DeleteBYId(Integer idProjet) {
        tacheBusiness.deleteByProject(FindById(idProjet).getId());
        projetRepository.deleteById(FindById(idProjet).getId());
    }

}
