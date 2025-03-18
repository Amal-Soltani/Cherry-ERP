package com.cherry.erp.company.business;

import com.cherry.erp.company.model.persistent.ReferenceConfig;
import org.json.JSONObject;
import org.springframework.lang.Nullable;

public interface ReferenceGenerationService {

    JSONObject generateReference(Integer year, String query, String prefix, String format);

    JSONObject generateReference(String query, String prefix, String format);

    String generateReference(String object, @Nullable Integer year);

    ReferenceConfig incrementReferenceConfigNumber(String object, @Nullable Integer year);
}
