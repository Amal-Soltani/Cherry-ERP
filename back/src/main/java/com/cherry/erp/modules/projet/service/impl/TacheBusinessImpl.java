package com.cherry.erp.modules.projet.service.impl;

import com.cherry.erp.common.utils.ReferenceConstant;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.production.repository.ProductRepository;
import com.cherry.erp.modules.projet.model.enums.StatutTacheEnum;
import com.cherry.erp.modules.projet.model.persistent.Planning;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.projet.repository.PlanningRepository;
import com.cherry.erp.modules.projet.repository.ProjetRepository;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import com.cherry.erp.modules.projet.repository.TacheRepository;
import com.cherry.erp.modules.projet.service.TacheBusiness;

import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import com.cherry.erp.modules.v2.hr.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class TacheBusinessImpl implements TacheBusiness {

    private final TacheRepository tacheRepository;
    private final ProductRepository productRepository;
    private final SpiUtils spiUtils;
    private final ProjetRepository projetRepository;
    private final EmployeeRepository employeeRepository;
    private final PlanningRepository planningRepository;

    @Override
    public String generateReference() {
        Integer nextRefNumber = tacheRepository.getNextReferenceNumber(spiUtils.findCurrentCompany().getId());
        // Format the reference with the prefix and zero padding
        String reference = ReferenceConstant.PRODUCTION_ORDER_REFERENCE_PREFIX
                + String.format(ReferenceConstant.PRODUCTION_ORDER_REFRENCE_FORMAT,nextRefNumber );
        return reference;
    }

    @Override
    public Page<Tache> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return tacheRepository.findByCompany(spiUtils.findCurrentCompany().getId(),pageParam);
    }

    @Override
    public Page<Tache> findByPageAndProject(Integer projectId,Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        Projects p = projetRepository.findByIdAndCompanyId(projectId,spiUtils.findCurrentCompany().getId()).orElse(null);
        return tacheRepository.findByPageAndProject(p.getId(),pageParam);
    }

    @Override
    public Page<Tache> findByPageAndStatus(String status,Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        StatutTacheEnum statutEnum = StatutTacheEnum.valueOf(status.toLowerCase());
        return tacheRepository.findByPageAndStatus(statutEnum,spiUtils.findCurrentCompany().getId(),pageParam);
    }

    @Override
    public Page<Tache> findByPageAndProjectAndStatus(Integer projectId,String status,Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        Projects p = projetRepository.findByIdAndCompanyId(projectId,spiUtils.findCurrentCompany().getId()).orElse(null);
        StatutTacheEnum statutEnum = StatutTacheEnum.valueOf(status.toLowerCase());
        return tacheRepository.findByPageAndProjectAndStatus(p.getId(),statutEnum,pageParam);
    }

    @Override
    public Tache Add(Tache tache) {
            if (tache.getId() != null && tache.getId() != 0) {
                Tache oldTache = FindById(tache.getId());
                spiUtils.setOldValues(tache, oldTache);
            }
        if (tache.getId() == null || tache.getId() == 0) {
            tache.setCompany(spiUtils.findCurrentCompany());
            tache.setReference(generateReference());
        }
        if (tache.getCompany() == null) {
            tache.setCompany(spiUtils.findCurrentCompany());
        }
            spiUtils.setModifiedByAndCreatedByFields(tache);
            tache.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return tacheRepository.save(tache);
    }

    @Override
    public Tache AddSousTache(Tache tache,Integer referenceProjet,Integer parent, String libelleProduit) {
        tache.setParent(parent);
        return tacheRepository.save(tache);
    }

    @Override
    public List<Tache> FindAll (){
        return tacheRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public Tache FindById(Integer id) {
        return tacheRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
    }

    @Override
    public List<Tache> FindSousTache(Integer tacheId,Integer projetId) {
        return tacheRepository.findSousTache(tacheId,projetId,spiUtils.findCurrentCompany().getId());
    }


    @Override
    public List<Tache> FindAllByProjet(Integer idProjet) {
        Projects p = projetRepository.findByIdAndCompanyId(idProjet,spiUtils.findCurrentCompany().getId()).orElse(null);
        return tacheRepository.findByProjetId(p.getId());
    }

    @Override
    public List<Tache> FindAllByClient(Integer clientId) {
        Employee c = employeeRepository.findByIdAndCompanyId(clientId,spiUtils.findCurrentCompany().getId()).orElse(null);
        return tacheRepository.findByClientId(c.getId());
    }

    @Override
    public void DeleteBYId(Integer id) {
        Tache t = FindById(id);
        List<Planning> l = planningRepository.findByTacheId(id);
        for (Planning p : l) {
            planningRepository.delete(p);
        }
        tacheRepository.deleteById(id);
    }
    @Override
    public void deleteByProduct(Integer id){
        Product p = productRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
        List<Tache> list = tacheRepository.findAllTacheByProduct(p.getId());
        for (Tache t : list) {
            DeleteBYId(t.getId());
        }
    }

    @Override
    public void deleteByProject(Integer id){
        Projects p = projetRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
        List<Tache> list = tacheRepository.findByProjetId(p.getId());
        for (Tache t : list) {
            DeleteBYId(t.getId());
        }
    }

    @Override
    public void DeleteBYIdAndProject(Integer id, Integer projetId) {
        Projects p = projetRepository.findByIdAndCompanyId(projetId,spiUtils.findCurrentCompany().getId()).orElse(null);
        tacheRepository.deleteByIdAndProjetId(id,p.getId());
    }

    @Override
    public Integer SumQtTotalProjet(Integer id) {
        Projects p = projetRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
        return tacheRepository.SumQtTotalProjet(p.getId());
    }

    @Override
    public Integer SumQtTotal() {
        return tacheRepository.SumQtTotal(spiUtils.findCurrentCompany().getId());
    }
}
