package com.egen.model.dto;

import com.egen.model.enums.DeliveryMethod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDeliveryDto {

    private String deliveryId;
    @JsonProperty(value = "deliveryMethod")
    private DeliveryMethod deliveryMethod;
    @JsonProperty(value = "deliveryCharges")
    private double deliveryCharges;
}
