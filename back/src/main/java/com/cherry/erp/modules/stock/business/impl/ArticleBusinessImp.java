package com.cherry.erp.modules.stock.business.impl;

import com.cherry.erp.common.utils.ReferenceConstant;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.production.repository.ProductRepository;
import com.cherry.erp.modules.stock.business.ArticleBusiness;
import com.cherry.erp.modules.stock.model.persistent.Article;
import com.cherry.erp.modules.stock.repository.ArticleRepository;
import com.cherry.erp.modules.production.repository.RawMaterialRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ArticleBusinessImp implements ArticleBusiness {

    private final ArticleRepository articleRepository;
    private final SpiUtils spiUtils;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductRepository productRepository;


    @Override
    public String generateReference() {
        Integer nextRefNumber = articleRepository.getNextReferenceNumber(spiUtils.findCurrentCompany().getId());
        // Format the reference with the prefix and zero padding
        String reference = ReferenceConstant.ARTICLE_REFERENCE_PREFIX
                + String.format(ReferenceConstant.ARTICLE_REFRENCE_FORMAT,nextRefNumber );

        return reference;
    }

    @Override
    public Article add(Article article) {
        if (article.getId() != null && article.getId() != 0) {
            Article oldArticle = FindByID(article.getId());
            spiUtils.setOldValues(article, oldArticle);
        }
        if (article.getId() == null || article.getId() == 0) {
            article.setCompany(spiUtils.findCurrentCompany());
            article.setReference(generateReference());
        }
        if (article.getCompany() == null) {
            article.setCompany(spiUtils.findCurrentCompany());
        }

        spiUtils.setModifiedByAndCreatedByFields(article);
        article.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());
        article.setLibelle("[" + article.getReference() + "] " + article.getName()) ;

        return articleRepository.save(article);

    }

    @Override
    public Page<Article> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return articleRepository.findByCompany(spiUtils.findCurrentCompany().getId(),pageParam);
    }
    @Override
    public List<Article> FindAll() {
        return articleRepository.findByCompanyIdByOrderByModificationDateDesc(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Article> FindAllExecptUsed(Integer idProduct) {
        return articleRepository.FindAllExecptUsed(idProduct,spiUtils.findCurrentCompany().getId());
    }

    @Override
    public Article FindByLibelle(String libelle) {
        try {
            String  decodedLibelle = URLDecoder.decode(libelle, "UTF-8");
            return articleRepository.findByLibelle(decodedLibelle,spiUtils.findCurrentCompany().getId());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    @Override
    public Article FindByID(Integer id) {
        return articleRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
    }

    @Override
    public List<Article> FindByProduct(Integer id) {
        Product p = productRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
        return articleRepository.findByProduct(p.getId());
    }

    @Override
    public void DeleteById(Integer id) {
        rawMaterialRepository.deleteByArticle(FindByID(id).getId());
        articleRepository.deleteById(FindByID(id).getId());
    }


}
