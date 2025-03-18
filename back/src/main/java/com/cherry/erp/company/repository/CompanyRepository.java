package com.cherry.erp.company.repository;

import com.cherry.erp.company.model.persistent.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Page<Company> findAllByOrderByCreationDateAsc(Pageable pageParam);

}
