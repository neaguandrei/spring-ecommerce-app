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
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest(classes = {CommerceApplication.class}, webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GetPaymentsTest {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Test
    public void testGetProductsReturn200Ok() {
        RequestEntity<?> requestEntity = RequestEntity.get(TestUtils.createUri("/payments/users/1"))
                .header(AUTHORIZATION, createAuthorisationToken())
                .header(CONTENT_TYPE, "application/hal+json")
                .build();

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(requestEntity, String.class);
        String response = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThatJson(response).field("_embedded", "payments", "[0]", "paymentMethod").matches(".*");
        assertThatJson(response).field("_embedded", "payments", "[0]", "_links", "self", "href").matches(".*/payments/.*");
    }

    @Test
    public void testGetPaymentsMissingTokenReturn401Unauthorized() {
        RequestEntity<?> requestEntity = RequestEntity.get(TestUtils.createUri("/payments/users/1"))
                .build();

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(requestEntity, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }
}
