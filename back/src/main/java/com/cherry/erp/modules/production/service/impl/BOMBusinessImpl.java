package com.cherry.erp.modules.production.service.impl;

import com.cherry.erp.common.utils.ReferenceConstant;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.production.model.persistent.BOM;
import com.cherry.erp.modules.production.repository.BOMRepository;
import com.cherry.erp.modules.production.service.BOMBusiness;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.production.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class BOMBusinessImpl implements BOMBusiness {

    private final BOMRepository BOMRepository;
    private final ProductRepository productRepository;
    private final SpiUtils spiUtils;

    @Override
    public String generateReference() {
        Integer nextRefNumber = BOMRepository.getNextReferenceNumber(spiUtils.findCurrentCompany().getId());
        // Format the reference with the prefix and zero padding
        String reference = ReferenceConstant.BOM_REFERENCE_PREFIX
                + String.format(ReferenceConstant.BOM_REFRENCE_FORMAT,nextRefNumber );

        return reference;
    }


    @Override
    public BOM Add(BOM nomenclature, Integer idProduct) {
        Product p = productRepository.findByIdAndCompanyId(idProduct,spiUtils.findCurrentCompany().getId()).orElse(null);

        if (nomenclature.getId() != null && nomenclature.getId() != 0 ) {
            BOM oldNomenclature = p.getBOM();
            spiUtils.setOldValues(nomenclature, oldNomenclature);

            spiUtils.setModifiedByAndCreatedByFields(nomenclature);
            nomenclature.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

            return BOMRepository.save(nomenclature);
        }else {
            spiUtils.setModifiedByAndCreatedByFields(nomenclature);
            nomenclature.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());
            nomenclature.setReference(generateReference());
            p.setBOM(BOMRepository.save(nomenclature));
            return p.getBOM();
        }

    }

    @Override
    public void DeleteBYId(Integer id) {
        Product p = productRepository.FindByNomenclature(id,spiUtils.findCurrentCompany().getId());
        BOM bom = p.getBOM();
        p.setBOM(null);
        BOMRepository.deleteById(bom.getId());
    }
}
