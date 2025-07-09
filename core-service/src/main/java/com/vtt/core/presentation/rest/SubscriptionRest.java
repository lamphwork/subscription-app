package com.vtt.core.presentation.rest;

import com.vtt.core.domain.model.Subscription;
import com.vtt.core.domain.port.usecase.SubscriptionUseCase;
import com.vtt.core.domain.port.usecase.params.AddSubscriptionInput;
import com.vtt.core.presentation.rest.request.AddSubscriptionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionRest {

    private final SubscriptionUseCase subscriptionUseCase;

    @PostMapping
    ResponseEntity<Void> createSubscription(@Valid @RequestBody AddSubscriptionRequest request) {
        AddSubscriptionInput input = new AddSubscriptionInput();
        BeanUtils.copyProperties(request, input);
        subscriptionUseCase.addSubscription(input);
        return ResponseEntity.ok().build();
    }
}
