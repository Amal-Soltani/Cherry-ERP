package com.cherry.erp.modules.documentaire.service;

import com.cherry.erp.modules.documentaire.model.persistent.Document;
import com.cherry.erp.modules.production.model.persistent.Product;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;


import java.io.IOException;
import java.util.List;

public interface DocumentBusiness {
    @Transactional(propagation = REQUIRED)
    public Document store(MultipartFile file,Integer idProduct,String indice) throws IOException;
    Document Add (Integer id, String indice );
    public Document getFile(Integer id);
    public Page<Document> findByPageAndProduct(Integer page, Integer pageSize, Integer projectId);
    public List<Document> FindByProduct(Integer idProduct);
    public Document FindOne(String ref);

    public void deleteByProduct(Integer id);
    @Transactional(propagation = REQUIRED)
    public  void DeleteBYId(Integer id);



}
