package com.cherry.erp.common.repository;

import com.cherry.erp.common.model.SearchParam;
import com.cherry.erp.common.model.persistent.GenericEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GenericSearchRepository<T extends GenericEntity> {

    @Transactional
    List<T> findByParams(List<SearchParam> searchParams);

    @Transactional
    Page<T> findByParams(List<SearchParam> searchParams, Pageable pageable);

    @Transactional
    List<T> findByParam(String param);

    @Transactional
    Page<T> findByParam(String param, Pageable pageable);
}
