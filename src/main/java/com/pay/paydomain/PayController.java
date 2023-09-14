package com.pay.paydomain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
                          @RequestParam Integer amount,
                          @RequestParam String paymentType,
                          Model model){
        InformationDto informationDto = new InformationDto();
        informationDto.setPaymentKey(paymentKey);
        informationDto.setOrderId(orderId);
        informationDto.setAmount(amount);
        informationDto.setPaymentType(paymentType);
        payService.saveInformation(informationDto);
        model.addAttribute("json", payService.requestFinalPayment(paymentKey, orderId, amount));
        return "success";
    }
}
