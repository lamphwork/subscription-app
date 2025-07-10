package com.vtt.core.presentation.rest;

import com.vtt.core.domain.port.usecase.CustPaymentUseCase;
import com.vtt.core.domain.port.usecase.params.CreateCustPaymentInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cust-payments")
@RequiredArgsConstructor
public class CustPaymentRest {

    private final CustPaymentUseCase custPaymentUseCase;

    @PostMapping
    public ResponseEntity<String> createCustPayment(@Valid @RequestBody CreateCustPaymentInput input) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(custPaymentUseCase.createCustPayment(input));
    }
}
