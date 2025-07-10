package com.vtt.core.domain.port.repository;

import com.vtt.core.domain.model.CustPayment;

import java.util.List;

public interface CustPaymentRepository {

    CustPayment save(CustPayment custPayment);

    List<CustPayment> findRenewableToday();

}
