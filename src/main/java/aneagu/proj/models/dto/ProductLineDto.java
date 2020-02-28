package aneagu.proj.models.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="productLine")
public class ProductLineDto {

    private String productLine;

    @NotNull(message = "Description can't be empty.")
    private String textDescription;

    private byte[] image;

    private ProductDto productDto;
}
