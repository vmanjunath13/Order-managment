package com.egen.model.dto;

import com.egen.model.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPaymentDto {

    private String paymentId;
    @JsonProperty(value = "paymentMethod")
    private PaymentMethod paymentMethod;
    @JsonProperty(value = "paymentDate")
    private ZonedDateTime paymentDate;
    @JsonProperty(value = "paymentConfirmationNumber")
    private String paymentConfirmationNumber;


}
