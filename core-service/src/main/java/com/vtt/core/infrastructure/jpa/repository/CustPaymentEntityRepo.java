package com.vtt.core.infrastructure.jpa.repository;

import com.vtt.core.infrastructure.jpa.entities.CustPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustPaymentEntityRepo extends JpaRepository<CustPaymentEntity, String> {
}
