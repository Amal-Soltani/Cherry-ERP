package com.cherry.erp.modules.production.service;

import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;


import java.util.List;

@Transactional
public interface ProductBusiness {

    @Transactional(propagation = REQUIRED)
    public Product Add (Product product);
    public String generateReference();

    public Page<Product> findByPage(Integer page, Integer pageSize);
    public Page<Product> findByPageAndType( String type, Integer page, Integer pageSize);
    public Page<Product> findByPageAndProject(Integer projectId, Integer page, Integer pageSize);

    @Transactional(propagation = REQUIRED)
    public Product UpdateByTache(Product product, Integer idTache);
    @Transactional(propagation = REQUIRED)
    public Product AffecterNomenclature(Integer id, Integer idNomenclature);

    public Product FindById(Integer id);
    public Product FindNomenclature(Integer id);
    public List<Product> FindAvailableByProject(Integer idProjet);
    public List<Product> FindAvailable();
    public Product FindByTache(Integer idTache);
    public Product FindByReference(String reference);

    @Transactional(propagation = REQUIRED)
    public void DeleteById(Integer id);

    public List<Product> findAllNomenclatureDesc();
    public Product FindByLibelle(String libelle);

    public List <Product> FindAllByProject(Integer idProjet);
    public List <Product> FindAllExceptParent(Integer id);

    public List <Product> FindAllByCompany();

    public List<Product>findByParent(Integer productId);


    }
