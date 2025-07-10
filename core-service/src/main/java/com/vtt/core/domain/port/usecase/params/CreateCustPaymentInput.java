package com.vtt.core.domain.port.usecase.params;

import lombok.Data;

import java.math.BigDecimal;

@Data
/**
 * Thông tin đăng ký thanh toán tự động của thuê bao
 */
public class CreateCustPaymentInput {

    private String custId;

    private String productCode;

    private Boolean autoRenew;
}
