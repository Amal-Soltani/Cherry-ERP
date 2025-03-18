package com.cherry.erp.modules.stock.business;

import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.stock.model.persistent.Article;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface ArticleBusiness {
    @Transactional(propagation = REQUIRED)
    public Article add (Article article);
    public String generateReference();
    public Page<Article> findByPage(Integer page, Integer pageSize);
    public List<Article> FindAll();
    public List<Article> FindAllExecptUsed(Integer idProduct);
    public Article FindByLibelle(String libelle);
    public Article FindByID(Integer id);
    public List<Article> FindByProduct(Integer id);
    @Transactional(propagation = REQUIRED)
    public void DeleteById(Integer id);
}
