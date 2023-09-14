package com.pay.paydomain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Information {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentKey;
    private String orderId;
    private int amount;
    private String paymentType;

    @Builder
    public Information(String paymentKey, String orderId, int amount, String paymentType) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentType = paymentType;
    }
}
