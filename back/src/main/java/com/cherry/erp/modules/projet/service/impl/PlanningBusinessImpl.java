package com.cherry.erp.modules.projet.service.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.company.business.CompanyBusiness;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.projet.model.persistent.Planning;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import com.cherry.erp.modules.projet.repository.PlanningRepository;
import com.cherry.erp.modules.projet.repository.ProjetRepository;
import com.cherry.erp.modules.projet.repository.TacheRepository;
import com.cherry.erp.modules.projet.service.PlanningBusiness;
import com.cherry.erp.modules.projet.service.ProjetBusiness;
import com.cherry.erp.modules.projet.service.TacheBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.DELETE_KO_CODE;
import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.RESOURCE_NOT_FOUND;

@Service
@Slf4j
@AllArgsConstructor
public class PlanningBusinessImpl implements PlanningBusiness {

    private final PlanningRepository planningRepository;
    private final SpiUtils spiUtils;
    private final ProjetRepository projetRepository;
    private final ProjetBusiness projetBusiness;
    private final TacheRepository tacheRepository;
    private final CompanyBusiness companyBusiness;
    private final TacheBusiness tacheBusiness;




    @Override
    public Planning AffecterProjet(Planning pilotage, Integer projectId) {
        if (pilotage.getId() != null && pilotage.getId() != 0) {
            Planning oldPilotage = FindById(pilotage.getId());
            spiUtils.setOldValues(pilotage, oldPilotage);
        }

            Projects project = projetRepository.findByIdAndCompanyId(projectId, spiUtils.findCurrentCompany()
                    .getId())
                    .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
            pilotage.setProjects(project);

        spiUtils.setModifiedByAndCreatedByFields(pilotage);
        pilotage.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return planningRepository.save(pilotage);
    }

    @Override
    public Planning Add(Planning pilotage) {
        if (pilotage.getId() != null && pilotage.getId() != 0) {
            Planning oldPilotage = FindById(pilotage.getId());
            spiUtils.setOldValues(pilotage, oldPilotage);
        }

        spiUtils.setModifiedByAndCreatedByFields(pilotage);
        pilotage.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return planningRepository.save(pilotage);
    }

    @Override
    public Planning UpdateDates(Integer id, String dateStart, String dateEnd) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Planning pilotage = planningRepository.findById(id)
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
        if (pilotage != null) {
            try {
                pilotage.setDateDebut(format.parse(dateStart));
                pilotage.setDateFin(format.parse(dateEnd));
                return planningRepository.save(pilotage);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Page<Planning> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        Company company = companyBusiness.getCurrentCompany();
        return planningRepository.findByCompany(company.getId(),pageParam);
    }

    @Override
    public Page<Planning> findByPageAndProject(Integer projectId,Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        Projects projects = projetBusiness.FindById(projectId);
        return planningRepository.findByPageAndProject(projects.getId(),pageParam);
    }

    @Override
    public Page<Planning> findByPageAndTache(Integer tacheId,Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        Tache tache = tacheBusiness.FindById(tacheId);
        return planningRepository.findByPageAndTache(tache.getId(),pageParam);
    }

    @Override
    public Planning FindById(Integer id) {
        return planningRepository.findById(id).orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public List<Planning> FindByProject(Integer projectId) {
        Projects project = projetRepository.findByIdAndCompanyId(projectId,spiUtils
                .findCurrentCompany().getId()).orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
        return planningRepository.findByProjectsId(project.getId());
    }

    @Override
    public List<Planning> FindAll() {
        List<Tache> tache = tacheBusiness.FindAll();
        if (tache != null){
            List<Planning> pilotages = new ArrayList<>();
            for (Tache t : tache) {
                pilotages.addAll(FindByTache(t.getId()));
            }
            return pilotages;
        }
        else{
            return null;
        }
    }

    @Override
    public Planning FindByIdAndTache(Integer id, String refOF) {
        Tache t = tacheRepository.findByReference(refOF);
        return planningRepository.findByIdAndTacheId(id,
                tacheRepository.findByIdAndCompanyId(t.getId(),
                        spiUtils.findCurrentCompany().getId())
                        .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode())).getId());
    }

    @Override
    public List<Planning> FindByTache(Integer idOF) {
        Tache t = tacheRepository.findByIdAndCompanyId(idOF, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
        return planningRepository.findByTacheId(t.getId());
    }

    @Override
    public void Delete(Integer id, Integer employeeId) {
        try {
            Planning pilotage = planningRepository.findByIdAndEmployeeId(id,employeeId);
            pilotage.setProjects(null);
            pilotage.setProductionLigne(null);
            pilotage.setEmployee(null);
            pilotage.setTache(null);
            planningRepository.delete(pilotage);        } catch (Exception e) {
            log.error("Error while deleting incident : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }

    }

    @Override
    public List<Object[]> TRSByProject(Integer id) {
        return planningRepository.findTRSByProject(id, spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> TRSByPhaseProject(Integer id) {
        return planningRepository.findTRSByPhaseProject(id, spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> TRSByEmpProject(Integer id) {
        return planningRepository.findTRSByEmpProject(id, spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> TRSByEquipProject(Integer id) {
        return planningRepository.findTRSByEquipProject(id, spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> TRSByLigneProject(Integer id) {
        return planningRepository.findTRSByLigneProject(id, spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> TRSByAllProjects() {
        return planningRepository.findTRSByAllProjects(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> TRSByPhase() {
        return planningRepository.findTRSByPhase(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> TRSByEmp() {
        return planningRepository.findTRSByEmp(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> TRSByEquip() {
        return planningRepository.findTRSByEquip( spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> TRSByLigne() {
        return planningRepository.findTRSByLigne(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> StaticOF(Integer id) {
        return planningRepository.findStaticOF(id,spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> QteProduiteGammeEmp(Integer id) {
        return planningRepository.QteProduiteGammeEmp(id,spiUtils.findCurrentCompany().getId());
    }


}
