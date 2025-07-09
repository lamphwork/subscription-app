package com.vtt.common.http;

import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.ReactorClientHttpRequestFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;

@Slf4j
public abstract class BaseHttpClient {

    Integer getConnectTimeout() {
        return 30000;
    }

    Integer getReadTimeout() {
        return 30000;
    }

    Integer getRetryCount() {
        return 3;
    }

    Long getRetryDelay() {
        return 5000L;
    }

    ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        return execution.execute(request, body);
    }

    public RestClient getRestClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getConnectTimeout())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getReadTimeout());

        return RestClient.builder()
                .requestFactory(new ReactorClientHttpRequestFactory(httpClient))
                .requestInterceptor(this::intercept)
                .build();
    }

    public ResponseEntity<String> postJSON(String url, MultiValueMap<String, String> headers, Object body) {
        return post(url, headers, body, false);
    }

    public ResponseEntity<String> postForm(String url, MultiValueMap<String, String> headers, MultiValueMap<String, Object> body) {
        return post(url, headers, body, true);
    }

    public ResponseEntity<String> post(String url, MultiValueMap<String, String> headers, Object body, boolean isForm) {
        RetryTemplate retryTemplate = new RetryTemplate();
        RetryPolicy retryPolicy = new SimpleRetryPolicy(getRetryCount());
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(getRetryDelay());

        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate.execute(retryContext -> getRestClient().post()
                .uri(url)
                .headers(h -> {
                    if (headers != null) {
                        h.putAll(headers);
                    }
                })
                .body(body != null ? (BodyInserters.fromValue(body)) : BodyInserters.empty())
                .contentType(isForm ? MediaType.MULTIPART_FORM_DATA : MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        (request, response) -> handle(retryContext, request, response)
                )
                .toEntity(String.class));
    }

    public ResponseEntity<String> get(String url, MultiValueMap<String, String> headers) {
        RetryTemplate retryTemplate = new RetryTemplate();
        RetryPolicy retryPolicy = new SimpleRetryPolicy(getRetryCount());
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(getRetryDelay());

        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate.execute(retryContext -> getRestClient().get()
                .uri(url)
                .headers(h -> {
                    if (headers != null) {
                        h.putAll(headers);
                    }
                })
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        (request, response) -> handle(retryContext, request, response)
                )
                .toEntity(String.class));
    }

    void handle(RetryContext retryContext, HttpRequest request, ClientHttpResponse response) throws IOException {
        int currentAttempts = retryContext.getRetryCount() + 1;
        log.info("Retry attempt #{} of {}", currentAttempts, getRetryCount());
        if (currentAttempts >= getRetryCount()) {
            log.info("Retry failure");
        } else {
            throw new RuntimeException(response.getStatusText());
        }
    }

}
