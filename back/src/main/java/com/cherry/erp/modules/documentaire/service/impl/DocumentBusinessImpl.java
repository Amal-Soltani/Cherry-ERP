package com.cherry.erp.modules.documentaire.service.impl;

import com.cherry.erp.common.utils.ReferenceConstant;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.documentaire.service.DocumentBusiness;
import com.cherry.erp.modules.documentaire.model.persistent.Document;
import com.cherry.erp.modules.documentaire.repository.DocumentRepository;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.production.repository.ProductRepository;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.util.StringUtils;

import javax.print.Doc;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class DocumentBusinessImpl implements DocumentBusiness {

    private final DocumentRepository documentRepository;
    private final ProductRepository productRepository;
    private final SpiUtils spiUtils;


    public String generateProjectReference() {
        Integer nextRefNumber = documentRepository.getNextReferenceNumber(spiUtils.findCurrentCompany().getId());
        // Format the reference with the prefix and zero padding
        String reference = ReferenceConstant.DOCUMENT_REFERENCE_PREFIX
                + String.format(ReferenceConstant.DOCUMENT_REFRENCE_FORMAT,nextRefNumber );

        return reference;
    }


    @Override
    public Document store(MultipartFile file,Integer idProduct,String indice) throws IOException {
        Document document = new Document();

        spiUtils.setModifiedByAndCreatedByFields(document);
        document.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        document.setReference(generateProjectReference());
        document.setName(fileName);
        document.setIndice(indice);
        document.setType(file.getContentType());
        document.setData(file.getBytes());
        document.setSize(file.getSize());
        document.setProduct(productRepository.findByIdAndCompanyId(idProduct,spiUtils.findCurrentCompany().getId()).orElse(null));
        return documentRepository.save(document);
    }

    @Override
    public Document Add(Integer id, String indice) {
        Document document = documentRepository.getDocumentByCompany(id, spiUtils.findCurrentCompany().getId());
        document.setIndice(indice);
        return documentRepository.save(document);
    }

    public Document getFile(Integer id) {
        return documentRepository.getDocument(id,spiUtils.findCurrentCompany().getId());
    }

    public Page<Document> findByPageAndProduct(Integer page, Integer pageSize, Integer idProduct) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return documentRepository.findByPageAndProduct(idProduct,spiUtils.findCurrentCompany().getId(),pageParam);
    }

    @Override
    public List<Document> FindByProduct(Integer idProduct) {
        return documentRepository.findByProduct(idProduct,spiUtils.findCurrentCompany().getId());
    }

    @Override
    public Document FindOne(String ref) {
        return documentRepository.findOne(ref,spiUtils.findCurrentCompany().getId());
    }
    @Override
    public void deleteByProduct(Integer id){
        Product p = productRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
        List<Document> list = FindByProduct(p.getId());
        for (Document t : list) {
            DeleteBYId(t.getId());
        }
    }

    @Override
    public void DeleteBYId(Integer id) {
        documentRepository.deleteById(getFile(id).getId());
    }
}
