package com.cherry.erp.common.business;

import com.cherry.erp.common.model.SearchParam;
import com.cherry.erp.common.model.persistent.GenericEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdvancedSearchService<T extends GenericEntity> {

    Page<T> findByParams(List<SearchParam> searchParams, Integer page, Integer pageSize);

    List<T> findByParams(List<SearchParam> searchParams);

}
