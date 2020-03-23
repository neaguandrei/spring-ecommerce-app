package aneagu.proj.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ProductCategory {
    MONITORS("Monitors"),
    HARDWARE("Hardware"),
    PERIPHERALS("Peripherals"),
    NONE("None");

    private String value;

    public static ProductCategory fromValue(String value) {
        return Arrays.stream(values())
                .filter(productCategory -> productCategory.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
