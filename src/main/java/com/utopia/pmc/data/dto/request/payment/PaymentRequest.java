package com.utopia.pmc.data.dto.request.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentRequest {
    private Long paymentId;
    private String bankCode;
    private String language;
}
