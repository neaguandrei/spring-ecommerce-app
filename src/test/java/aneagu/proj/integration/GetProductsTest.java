package aneagu.proj.integration;


import aneagu.proj.CommerceApplication;
import aneagu.proj.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static aneagu.proj.utils.TokenGenerator.createAuthorisationToken;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@SpringBootTest(classes = {CommerceApplication.class}, webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GetProductsTest {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Test
    public void testGetProductsReturn200() {
        RequestEntity<?> requestEntity = RequestEntity.get(TestUtils.createUri("/products",
                "page", "size", "sort", "sortedParam", "searchKey", "productCategory"))
                .header(AUTHORIZATION, createAuthorisationToken())
                .build();

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(requestEntity, String.class);
        String response = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThatJson(response).field("content", "[0]", "name").matches(".*");
        assertThatJson(response).field("content", "[0]", "id").matches(".*");
        assertThatJson(response).field("content", "[0]", "description").matches(".*");
        assertThatJson(response).field("content", "[0]", "quantityInStock").matches(".*");
    }
}
