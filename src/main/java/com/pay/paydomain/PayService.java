package com.pay.paydomain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
