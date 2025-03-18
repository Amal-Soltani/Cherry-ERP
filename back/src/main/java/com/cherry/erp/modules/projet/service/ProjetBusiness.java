package com.cherry.erp.modules.projet.service;

import com.cherry.erp.modules.projet.model.persistent.Projects;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
@Transactional
public interface ProjetBusiness {
    @Transactional(propagation = REQUIRED)
    public Projects add (Projects projet);
    public Projects FindById(Integer idProjet);
    public String generateReference();
    public Page<Projects> findByPage(Integer page, Integer pageSize);
    public Page<Projects> findByPageAndStatus( Integer page, Integer pageSize, String status);

    public List<Projects> FindAll();
    @Transactional(propagation = REQUIRED)
    public  void DeleteBYId(Integer idProjet);
}
