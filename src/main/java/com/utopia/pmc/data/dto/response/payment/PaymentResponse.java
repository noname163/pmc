package com.utopia.pmc.data.dto.response.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentResponse {
    private String code;
    private String message;
    private String data;
}
