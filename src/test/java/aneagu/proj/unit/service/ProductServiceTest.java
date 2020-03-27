package aneagu.proj.unit.service;

import aneagu.proj.models.domain.Product;
import aneagu.proj.models.dto.ProductDto;
import aneagu.proj.models.enums.ProductCategory;
import aneagu.proj.repository.ProductRepository;
import aneagu.proj.service.MapperService;
import aneagu.proj.service.ProductServiceImpl;
import aneagu.proj.utils.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;


import java.util.Collections;

import static aneagu.proj.utils.TestUtils.createProductDto;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MapperService mapperService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testGetProductsGivenCorrectDataReturnCorrectData() {
        Mockito.when(productRepository.findAllByNameAndCategory(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(new Product())));
        Mockito.when(productRepository.findAllByName(Mockito.any(), Mockito.any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(new Product())));
        Mockito.when(productRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(new Product())));
        Mockito.when(mapperService.convertProductToProductDto(Mockito.any(Product.class)))
                .thenReturn(createProductDto());

        Page<ProductDto> productDtoPage = productService.getProducts("Test", ProductCategory.MONITORS, null);
        ProductDto actual = productDtoPage.stream().findFirst().orElse(null);
        ProductDto expected = TestUtils.createProductDto();
        assertNotNull(productDtoPage);
        Assert.assertThat(actual, is(expected));
    }

}
