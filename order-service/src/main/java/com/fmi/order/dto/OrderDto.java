package com.fmi.order.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fmi.common.validation.OneOf;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    private Long id;

    @Size(max = 450)
    private String comment;

    @NotNull
    @OneOf(enumClass = Status.class, message = "Incorrect status type.")
    private String status;

    private Long userId;

    /*
     * The client should call once again with the productIds to obtain the product values when the order is accesed.
     *
     * */
    @JsonProperty(value = "products")
    private List<Long> productIds;

    private Long paymentId;

    public enum Status {
        APPROVED,
        CANCELLED,
        PENDING;
    }
}