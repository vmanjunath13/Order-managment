package com.egen.model.dto;

import com.egen.model.entity.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemDto {

    private String itemId;

    @JsonProperty(value = "itemName")
    private String itemName;

    @JsonProperty(value = "itemQty")
    private int itemQty;

    @JsonProperty(value = "subtotal")
    private double subtotal;

    @JsonProperty(value = "tax")
    private double tax;

    @JsonProperty(value = "total")
    private double total;

    @JsonProperty(value = "orders")
    private Order orders;

}
