package aneagu.proj.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class OrderDto {

    private Long id;

    @NotNull(message = "Date can't be empty.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;

    @Size(max = 450)
    private String comment;

    private Long userId;
}
