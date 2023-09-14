package com.pay.paydomain;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;
    @GetMapping("/product")
    public String product(){
        return "product";
    }

    @PostMapping("/order")
    public String order(Model model, @RequestParam(value = "product") String product, @RequestParam(value = "price") String price){
        model.addAttribute("product", product);
        model.addAttribute("price", price);
        return "pay";
    }

    @GetMapping("/fail")
    public String fail(){
        return "fail";
    }

    @GetMapping("/success")
    public String success(@RequestParam String paymentKey,
                          @RequestParam String orderId,
                          @RequestParam int amount,
                          @RequestParam String paymentType,
                          Model model){
        InformationDto informationDto = new InformationDto();
        informationDto.setPaymentKey(paymentKey);
        informationDto.setOrderId(orderId);
        informationDto.setAmount(amount);
        informationDto.setPaymentType(paymentType);
        payService.saveInformation(informationDto);


//        curl --request POST \
//        --url https://api.tosspayments.com/v1/payments/confirm \
//        --header 'Authorization: Basic dGVzdF9za19QOUJSUW15YXJZRzUxdk1uRExYM0owN0t6TE5rOg==' \
//        --header 'Content-Type: application/json' \
//        --header 'Idempotency-Key: a6a498c4-6f61-4183-a2ff-80176e69a067' \
//        --data '{"paymentKey":"5zJ4xY7m0kODnyRpQWGrN2xqGlNvLrKwv1M9ENjbeoPaZdL6","orderId":"a4CWyWY5m89PNh7xJwhk1","amount":15000}'

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        String testSecretApiKey = "test_sk_P9BRQmyarYG51vMnDLX3J07KzLNk";
        String key = new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));
        headers.setBasicAuth(key);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        JSONObject param = new JSONObject();
        param.put("orderId", orderId);
        param.put("amount", amount);
//        model.addAttribute(
//                "tossapi",
//                rest.postForEntity(
//                        "http://localhost:8080/v1/payments/confirm",
//                        new HttpEntity<>(param, headers),
//                        String.class)
//                        .getBody()
//        );

        return "success";
    }
}
