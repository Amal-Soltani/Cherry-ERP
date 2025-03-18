package com.cherry.erp.modules.projet.service;

import com.cherry.erp.modules.projet.model.persistent.Tache;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface TacheBusiness {
    @Transactional(propagation = REQUIRED)
    public Tache AddSousTache(Tache tache, Integer referenceProjet, Integer parent, String libelleProduit);
    @Transactional(propagation = REQUIRED)
    public Tache Add(Tache tache);
    public String generateReference();
    public Page<Tache> findByPage(Integer page, Integer pageSize);
    public Page<Tache> findByPageAndProject(Integer projectId, Integer page, Integer pageSize);
    public Page<Tache> findByPageAndStatus(String status, Integer page, Integer pageSize);
    public Page<Tache> findByPageAndProjectAndStatus(Integer projectId,String status, Integer page, Integer pageSize);
    public List<Tache> FindAll ();
    public Tache FindById(Integer id);
    public List<Tache> FindSousTache (Integer tacheId,Integer projetId);
    public List<Tache> FindAllByProjet(Integer idProjet);
    public List<Tache> FindAllByClient(Integer tacheId);
    @Transactional(propagation = REQUIRED)
    public  void DeleteBYId(Integer id);
    @Transactional(propagation = REQUIRED)
    public void deleteByProduct(Integer id);
    @Transactional(propagation = REQUIRED)
    public void deleteByProject(Integer id);

    @Transactional(propagation = REQUIRED)
    public void DeleteBYIdAndProject(Integer id,Integer projetId);

    public Integer SumQtTotalProjet(Integer id);

    public Integer SumQtTotal();

}
