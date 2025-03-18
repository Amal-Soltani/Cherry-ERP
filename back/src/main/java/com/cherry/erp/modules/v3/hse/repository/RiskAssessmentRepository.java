package com.cherry.erp.modules.v3.hse.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RiskAssessmentRepository extends GenericRepository<RiskAssessment> {

}
