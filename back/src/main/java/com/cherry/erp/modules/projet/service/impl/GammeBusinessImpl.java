package com.cherry.erp.modules.projet.service.impl;

import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.production.repository.ProductRepository;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import com.cherry.erp.modules.projet.model.persistent.GmPhase;
import com.cherry.erp.modules.projet.repository.GammeRepository;
import com.cherry.erp.modules.projet.repository.GmPhaseRepository;
import com.cherry.erp.modules.projet.service.GammeBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class GammeBusinessImpl implements GammeBusiness {
    private final GammeRepository gammeRepository;
    private final SpiUtils spiUtils;
    private final ProductRepository productRepository;
    private final GmPhaseRepository gmPhaseRepository;

    @Override
    public Gamme Add(Integer productId) {
        Gamme gamme = new Gamme();

        gamme.setCompany(spiUtils.findCurrentCompany());
        gamme.setCompany(spiUtils.findCurrentCompany());

        Product p = productRepository.findByIdAndCompanyId(productId,spiUtils.findCurrentCompany().getId()).orElse(null);
        p.setGamme(gamme);
        spiUtils.setModifiedByAndCreatedByFields(gamme);
        gamme.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return gammeRepository.save(gamme);
    }

    @Override
    public Gamme FindByProduct(Integer id) {
        Product p = productRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
        return gammeRepository.findByProductId(p.getId());
    }


    @Override
    public void DeleteBYId(Integer id) {
        List<GmPhase> list = gmPhaseRepository.findByGammeId(id);
        if(!list.isEmpty()){
            for (GmPhase gm : list) {
                gm.setPhase(null);
                gm.setGamme(null);
                gmPhaseRepository.delete(gm);
            }
        }
        gammeRepository.deleteById(id);
    }

}
