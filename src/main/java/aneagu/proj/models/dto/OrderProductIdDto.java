package aneagu.proj.models.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class OrderProductIdDto implements Serializable {

    private Long orderId;

    private Long productId;
}
