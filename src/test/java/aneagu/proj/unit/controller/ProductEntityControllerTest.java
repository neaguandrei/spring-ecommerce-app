package aneagu.proj.unit.controller;

import aneagu.proj.controller.ProductController;
import aneagu.proj.models.dto.ProductDto;
import aneagu.proj.models.enums.ProductCategory;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.CoreMatchers.*;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductEntityControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    public void testGetProductsGivenCorrectDataReturnList() throws NotFoundException {
        Mockito.when(productService.getProducts(Mockito.anyString(), Mockito.any(ProductCategory.class), Mockito.any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(new ProductDto())));
        ResponseEntity<Page<ProductDto>> actual = productController.getProducts(0, 10, "DESC", "quantityInStock", "", "None");
        assertNotNull(actual);
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
    }

    @Test(expected = NotFoundException.class)
    public void testGetProductsGivenNotExistingResourceThrowNotFoundException() throws NotFoundException {
        Mockito.when(productService.getProducts(Mockito.anyString(), Mockito.any(ProductCategory.class), Mockito.any()))
                .thenThrow(NotFoundException.class);
        productController.getProducts(0, 10, "DESC", "quantityInStock", "", "None");
    }

}
