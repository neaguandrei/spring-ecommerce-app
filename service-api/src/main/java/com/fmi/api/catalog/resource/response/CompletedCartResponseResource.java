package com.fmi.api.catalog.resource.response;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletedCartResponseResource {

    @NotNull
    private Map<Long, Integer> products;

}
