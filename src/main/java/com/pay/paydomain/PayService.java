package com.pay.paydomain;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PayRepository payRepository;

    public void saveInformation(InformationDto informationDto){
        Information information = Information.builder()
                .paymentKey(informationDto.getPaymentKey())
                .amount(informationDto.getAmount())
                .orderId(informationDto.getOrderId())
                .paymentType(informationDto.getPaymentType())
                .build();
        payRepository.save(information);
    }

    @Transactional
    public PayDto requestFinalPayment(String paymentKey, String orderId, int amount) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        String key = "test_sk_P9BRQmyarYG51vMnDLX3J07KzLNk:";

        String encode = new String(Base64.getEncoder().encode(key.getBytes(StandardCharsets.UTF_8)));

        headers.setBasicAuth(encode);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        JSONObject json = new JSONObject();
        json.put("orderId", orderId);
        json.put("paymentKey", paymentKey);
        json.put("amount", amount);

        PayDto body = rest.postForEntity(
                "https://api.tosspayments.com/v1/payments/confirm",
                new HttpEntity<>(json, headers),
                PayDto.class
        ).getBody();


        return body;
    }

}
