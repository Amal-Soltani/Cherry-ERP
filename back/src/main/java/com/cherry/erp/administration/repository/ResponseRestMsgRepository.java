package com.cherry.erp.administration.repository;

import com.cherry.erp.administration.model.persistent.ResponseRestMsg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponseRestMsgRepository extends JpaRepository<ResponseRestMsg, Integer> {
    Optional<ResponseRestMsg> findByCode(String code);
}
