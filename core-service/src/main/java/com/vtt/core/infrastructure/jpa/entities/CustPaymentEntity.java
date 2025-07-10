package com.vtt.core.infrastructure.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "cust_payment")
public class CustPaymentEntity {

    @Id
    private String id;  // Mã định danh

    @Column(nullable = false)
    private String custId;  // Khóa ngoại đến bảng khác

    @Column(nullable = false)
    private String productCode;  // Mã sản phẩm

    private Boolean autoRenew;  // Tự động gia hạn

    private Instant createdAt;  // Thời điểm tạo bản ghi

    private Instant updatedAt;  // Thời điểm cập nhật bản ghi

    private String createdBy;   // Người tạo

    private String updatedBy;   // Người cập nhật
}
