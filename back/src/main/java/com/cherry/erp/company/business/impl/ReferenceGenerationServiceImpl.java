package com.cherry.erp.company.business.impl;

import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.company.business.ReferenceGenerationService;
import com.cherry.erp.company.model.persistent.ReferenceConfig;
import com.cherry.erp.company.repository.ReferenceConfigRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class ReferenceGenerationServiceImpl implements ReferenceGenerationService {

    private static final String YEAR_KEY = "year";
    private static final String NUM_KEY = "num";
    private static final String ALIAS_KEY = "alias";

    private final SpiUtils spiUtils;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ReferenceConfigRepository referenceConfigRepository;
    private final EntityManager entityManager;

    @Override
    public JSONObject generateReference(Integer year, String query, String prefix, String format) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("companyId", spiUtils.findCurrentCompany().getId())
                .addValue("year", year);
        JSONObject o = new JSONObject();
        Integer num = jdbcTemplate.queryForObject(query, parameters, Integer.class);
        DecimalFormat myFormatter = new DecimalFormat(format);
        o.put(NUM_KEY, myFormatter.format(num));
        o.put(YEAR_KEY, String.valueOf(year));
        o.put(ALIAS_KEY, prefix);

        return o;
    }

    @Override
    public JSONObject generateReference(String query, String prefix, String format) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("companyId", spiUtils.findCurrentCompany().getId());
        JSONObject o = new JSONObject();
        Integer num = jdbcTemplate.queryForObject(query, parameters, Integer.class);
        DecimalFormat myFormatter = new DecimalFormat(format);
        o.put(NUM_KEY, myFormatter.format(num));
        o.put(ALIAS_KEY, prefix);

        return o;
    }

    @Override
    public String generateReference(String object, @Nullable Integer year) {
        Integer companyId = spiUtils.findCurrentCompany().getId();
        String reference;
        ReferenceConfig referenceConfig;

        referenceConfig = referenceConfigRepository.findByCompanyIdAndObjectAndYearNull(companyId, object).orElse(null);
        if (Objects.isNull(referenceConfig)) {
            referenceConfig = referenceConfigRepository.findByCompanyIdAndObjectAndYear(companyId, object, year).orElse(null);
        } else {
            year = null;
        }

        if (Objects.isNull(referenceConfig)) {
            // if config does not exist, copy config from old reference config
            referenceConfig = referenceConfigRepository.findByCompanyIdAndObject(companyId, object).orElse(null);
            if (Objects.nonNull(referenceConfig)) {
                entityManager.detach(referenceConfig);
                referenceConfig.setId(0);
                referenceConfig.setYear(year);
                referenceConfig.setNumber(0);
            } else {
                // if no config is present, create a new default config for this object
                referenceConfig = createDefaultConfig(object, year);
            }
            // save reference config
            referenceConfigRepository.save(referenceConfig);
        }

        JSONObject o = new JSONObject();
        DecimalFormat myFormatter = new DecimalFormat(referenceConfig.getNumberFormat());
        o.put(NUM_KEY, myFormatter.format((long) referenceConfig.getNumber() + 1));
        if (year != null) {
            o.put(YEAR_KEY, String.valueOf(year));
        }
        o.put(ALIAS_KEY, referenceConfig.getPrefix());

        if (year == null) {
            String referenceTemplate = referenceConfig.getPrefix() != null ? referenceConfig.getPrefix() + "-{0}" : "{0}";
            reference = MessageFormat.format(referenceTemplate, String.valueOf(o.get(NUM_KEY)));
        } else {
            String referenceTemplate = referenceConfig.getPrefix() != null ? referenceConfig.getPrefix() + "-{0}/{1}" : "{0}/{1}";

            reference = MessageFormat.format(referenceTemplate, String.valueOf(o.get(NUM_KEY)), o.get(YEAR_KEY));
        }

        log.info("Reference [{}] generated for companyId: {}, object: {} and year: {}", reference, companyId, object, year);

        return reference;
    }

    @Override
    public ReferenceConfig incrementReferenceConfigNumber(String object, @Nullable Integer year) {
        ReferenceConfig referenceConfig = referenceConfigRepository.findByCompanyIdAndObjectAndYear(spiUtils.findCurrentCompany().getId(), object, year).orElse(null);

        if (Objects.nonNull(referenceConfig)) {
            referenceConfig.setNumber(referenceConfig.getNumber() + 1);
            return referenceConfigRepository.save(referenceConfig);
        }

        return referenceConfig;
    }

    private ReferenceConfig createDefaultConfig(String object, @Nullable Integer year) {
        ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setYear(year);
        referenceConfig.setNumber(0);
        referenceConfig.setCompany(spiUtils.findCurrentCompany());
        referenceConfig.setObject(object);
        referenceConfig.setNumberFormat("00000");
        return referenceConfig;
    }

}
