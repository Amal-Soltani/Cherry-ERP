package com.cherry.erp.modules.projet.service;

import com.cherry.erp.modules.projet.model.persistent.Planning;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface PlanningBusiness {
    @Transactional(propagation = REQUIRED)
    public Planning Add(Planning planning);
    @Transactional(propagation = REQUIRED)
    public Planning AffecterProjet (Planning planning, Integer projectId);
    @Transactional(propagation = REQUIRED)
    public Planning UpdateDates(Integer id, String dateStart,String dateEnd);

    public Page<Planning> findByPage(Integer page, Integer pageSize);
    public Page<Planning> findByPageAndProject(Integer projectId, Integer page, Integer pageSize);
    public Page<Planning> findByPageAndTache(Integer tacheId, Integer page, Integer pageSize);

    public Planning FindById(Integer id);
    public List<Planning> FindByProject(Integer projectId);
    public List<Planning> FindAll();
    public Planning FindByIdAndTache(Integer id,String refOF);
    public List<Planning> FindByTache(Integer idOF);

    public void Delete(Integer id,Integer employeeId);

    public List<Object[]> TRSByProject(Integer id);
    public List<Object[]> TRSByPhaseProject(Integer id);
    public List<Object[]> TRSByEmpProject(Integer id);
    public List<Object[]> TRSByEquipProject(Integer id);
    public List<Object[]> TRSByLigneProject(Integer id);
    public List<Object[]> TRSByAllProjects();

    public List<Object[]> TRSByPhase();
    public List<Object[]> TRSByEmp();
    public List<Object[]> TRSByEquip();
    public List<Object[]> TRSByLigne();

    public List<Object[]> StaticOF(Integer id);
    public List<Object[]> QteProduiteGammeEmp(Integer id);
}
