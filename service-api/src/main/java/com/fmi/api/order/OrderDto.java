package com.fmi.api.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fmi.common.validation.OneOf;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(max = 450)
    private String comment;

    @NotNull
    @OneOf(enumClass = StatusDto.class, message = "Incorrect status type.")
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userId;

    @NotNull
    @JsonProperty(value = "products")
    private List<Long> productIds;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long paymentId;

}