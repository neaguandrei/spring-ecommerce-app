package aneagu.proj.utils;

import aneagu.proj.models.dto.ProductDto;
import aneagu.proj.models.enums.ProductCategory;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    private static final String AUTHORIZATION = "Authorization";

    public static URI createUri(String uri, String... queryParams) {
        if (queryParams == null || queryParams.length == 0) {
            return URI.create(uri);
        }

        StringBuilder builder = new StringBuilder(uri);
        builder.append("?");

        List<String> queryParamsList = Arrays.asList(queryParams);
        queryParamsList
                .forEach(queryParam -> {
                    builder.append(queryParam);
                    if (queryParamsList.indexOf(queryParam) != queryParamsList.size() - 1) {
                        builder.append("&");
                    }
                });

        return URI.create(builder.toString());
    }

    public static ProductDto createProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(100L);
        productDto.setBuyPrice(100L);
        productDto.setDescription("Description");
        productDto.setName("Name");
        productDto.setCategory(ProductCategory.MONITORS.getValue());
        productDto.setQuantityInStock(12L);
        productDto.setProductLine("DELL");

        return productDto;
    }
}
