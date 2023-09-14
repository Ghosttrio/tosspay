package com.pay.paydomain;

import lombok.Data;

@Data
public class PayDto {

    private String orderId;
    private String orderName;
    private String requestedAt;
    private String approvedAt;
    private int totalAmount;
}
