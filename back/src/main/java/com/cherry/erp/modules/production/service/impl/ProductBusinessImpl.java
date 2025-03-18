package com.cherry.erp.modules.production.service.impl;
import com.cherry.erp.common.utils.ReferenceConstant;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.documentaire.repository.DocumentRepository;
import com.cherry.erp.modules.documentaire.service.DocumentBusiness;
import com.cherry.erp.modules.production.model.enums.TypeProductEnum;
import com.cherry.erp.modules.production.model.persistent.BOM;
import com.cherry.erp.modules.production.repository.RawMaterialRepository;
import com.cherry.erp.modules.production.repository.ProductRepository;
import com.cherry.erp.modules.production.repository.BOMRepository;
import com.cherry.erp.modules.production.repository.QuantityProductRepository;
import com.cherry.erp.modules.production.service.ProductBusiness;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.projet.repository.ProjetRepository;
import com.cherry.erp.modules.projet.service.GammeBusiness;
import com.cherry.erp.modules.projet.service.TacheBusiness;
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
public class ProductBusinessImpl implements ProductBusiness {

    private final RawMaterialRepository rawMaterialRepository;
    private final ProductRepository productRepository;
    private final SpiUtils spiUtils;
    private final BOMRepository BOMRepository;
    private final TacheBusiness tacheBusiness;
    private final GammeBusiness gammeBusiness;
    private  final ProjetRepository projetRepository;
    private final QuantityProductRepository quantityProductRepository;
    private final DocumentBusiness documentBusiness;

@Override
    public String generateReference() {
        Integer nextRefNumber = productRepository.getNextReferenceNumber(spiUtils.findCurrentCompany().getId());
        // Format the reference with the prefix and zero padding
        String reference = ReferenceConstant.PRODUCT_REFERENCE_PREFIX
                + String.format(ReferenceConstant.PRODUCT_REFRENCE_FORMAT,nextRefNumber );

        return reference;
    }

    @Override
    public Page<Product> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return productRepository.findByCompany(spiUtils.findCurrentCompany().getId(),pageParam);
    }

    @Override
    public Page<Product> findByPageAndType( String type, Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        TypeProductEnum typeEnum = TypeProductEnum.valueOf(type.toUpperCase());
        return productRepository.findByPageAndType(typeEnum,spiUtils.findCurrentCompany().getId(),pageParam);
    }

    @Override
    public Page<Product> findByPageAndProject(Integer projectId, Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        Projects p = projetRepository.findByIdAndCompanyId(projectId,spiUtils.findCurrentCompany().getId()).orElse(null);
        return productRepository.findByPageAndProject(p.getId(),pageParam);
    }

    @Override
    public Product Add(Product product) {
        if (product.getId() != null && product.getId() != 0) {
            Product oldProduct = FindById(product.getId());
            spiUtils.setOldValues(product, oldProduct);
        }
        if (product.getId() == null || product.getId() == 0) {
            product.setCompany(spiUtils.findCurrentCompany());
            product.setReference(generateReference());
        }
        if (product.getCompany() == null) {
            product.setCompany(spiUtils.findCurrentCompany());
        }
        spiUtils.setModifiedByAndCreatedByFields(product);
        product.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());
        product.setLibelle("[" + product.getReference() + "] " + product.getName()) ;

        return productRepository.save(product);

    }

    @Override
    public Product UpdateByTache(Product product, Integer idTache) {
        Product product1 = FindByTache(idTache);
        product1.setName(product.getName());
        product1.setBOM(product.getBOM());
        product1.setLibelle(product.getLibelle());
        product1.setMatiere(product.getMatiere());
        product1.setReference(product.getReference());
        product1.setTypeProduct(product.getTypeProduct());
        product1.setDesignation(product.getDesignation());
        product1.setRawMaterial(product.getRawMaterial());
        product1.setUnitMeasurement(product.getUnitMeasurement());
        return productRepository.save(product1);
    }


    @Override
    public Product FindByTache(Integer idTache) {
        return productRepository.FindProductByTache(idTache,spiUtils.findCurrentCompany().getId());
    }

    @Override
    public Product FindById(Integer id) {
        return productRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId()).orElse(null);
    }

    @Override
    public Product FindNomenclature(Integer id) {
        return productRepository.FindNomenclature(id,spiUtils.findCurrentCompany().getId());
    }

    @Override
    public Product FindByReference(String reference) {
        return productRepository.findByReference(reference);
    }

    @Override
    public List<Product> FindAvailableByProject(Integer ProjectId) {
        List <Product> liste = productRepository.FindAvailableProductsByProject(ProjectId,spiUtils.findCurrentCompany().getId());
        if(liste.size() > 0){
            return  liste;
        }else {
            return FindAvailable();
        }
    }

    @Override
    public List<Product> FindAvailable() {
        return productRepository.FindAvailableProducts(spiUtils.findCurrentCompany().getId());
    }


    @Override
    public Product AffecterNomenclature(Integer id, Integer idNomenclature) {
        BOM nomenclature = BOMRepository.findById(idNomenclature).orElse(null);
        Product product = productRepository.findById(id).orElse(null);
        product.setBOM(nomenclature);
        return productRepository.save(product);
    }

    @Override
    public void DeleteById(Integer id) {
        quantityProductRepository.deleteByParent(FindById(id).getId());
        quantityProductRepository.deleteByChild(FindById(id).getId());
        rawMaterialRepository.deleteByProduct(FindById(id).getId());
        Gamme gamme =  FindById(id).getGamme();
        if(gamme != null){gammeBusiness.DeleteBYId(gamme.getId());}
        tacheBusiness.deleteByProduct(FindById(id).getId());
        documentBusiness.deleteByProduct(FindById(id).getId());
        productRepository.deleteById(FindById(id).getId());
    }

    @Override
    public List<Product> findAllNomenclatureDesc() {
        return productRepository.FindAllDesc(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public Product FindByLibelle(String libelle) {
        try {
            String decodedLibelle = URLDecoder.decode(libelle, "UTF-8");
            return productRepository.findByLibelle(decodedLibelle);
        } catch (UnsupportedEncodingException e) {
          return null;
        }
    }


    @Override
    public List<Product> FindAllByProject(Integer ProjectId) {
        return productRepository.FindByProject(ProjectId, spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Product> FindAllExceptParent(Integer id) {
        return productRepository.FindAllExceptParent(id,spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Product> FindAllByCompany() {
        return productRepository.findByCompanyIdByOrderByModificationDateDesc(spiUtils.findCurrentCompany().getId()) ;
    }

    @Override
    public List<Product> findByParent(Integer productId) {
        Product p = productRepository.findByIdAndCompanyId(productId,spiUtils.findCurrentCompany().getId()).orElse(null);
        return productRepository.findByParent(p.getId());
    }


}
