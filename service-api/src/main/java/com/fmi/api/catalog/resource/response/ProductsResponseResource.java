package com.fmi.api.catalog.resource.response;

import com.fmi.api.catalog.ProductDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsResponseResource {

    @NotNull
    private List<ProductDto> products;

}
