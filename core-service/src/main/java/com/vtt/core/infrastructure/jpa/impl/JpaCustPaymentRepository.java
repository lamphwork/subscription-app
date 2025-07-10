package com.vtt.core.infrastructure.jpa.impl;

import com.vtt.core.domain.model.CustPayment;
import com.vtt.core.domain.port.repository.CustPaymentRepository;
import com.vtt.core.infrastructure.jpa.entities.CustPaymentEntity;
import com.vtt.core.infrastructure.jpa.repository.CustPaymentEntityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaCustPaymentRepository implements CustPaymentRepository {

    private final CustPaymentEntityRepo custPaymentEntityRepo;

    @Override
    public CustPayment save(CustPayment custPayment) {
        CustPaymentEntity entity = toEntity(custPayment);
        custPaymentEntityRepo.save(entity);
        return toModel(entity);
    }

    @Override
    public List<CustPayment> findRenewableToday() {
        return List.of();
    }

    /**
     * convert domain model to jpa entity
     *
     * @param custPayment domain model
     * @return entity
     */
    CustPaymentEntity toEntity(CustPayment custPayment) {
        CustPaymentEntity entity = new CustPaymentEntity();
        BeanUtils.copyProperties(custPayment, entity);
        return entity;
    }

    /**
     * convert jpa entity to domain model
     *
     * @param custPaymentEntity entity
     * @return domain model
     */
    CustPayment toModel(CustPaymentEntity custPaymentEntity) {
        return new CustPayment(
                custPaymentEntity.getId(),
                custPaymentEntity.getCustId(),
                custPaymentEntity.getProductCode(),
                custPaymentEntity.getAutoRenew(),
                custPaymentEntity.getCreatedAt(),
                custPaymentEntity.getUpdatedAt(),
                custPaymentEntity.getCreatedBy(),
                custPaymentEntity.getUpdatedBy()
        );
    }
}
