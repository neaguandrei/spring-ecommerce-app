package com.fmi.order.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fmi.common.validation.OneOf;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderDto {

    private Long id;

    @Size(max = 450)
    private String comment;

    @NotNull
    @OneOf(enumClass = Status.class, message = "Incorrect status type.")
    private String status;

    private Long userId;

    public enum Status {
        APPROVED,
        CANCELLED,
        PENDING;
    }
}