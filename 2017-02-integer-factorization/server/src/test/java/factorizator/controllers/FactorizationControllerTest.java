package factorizator.controllers;

import factorizator.controllers.FactorizationController;
import factorizator.services.PrimeFactorizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FactorizationControllerTest {

    @Mock
    PrimeFactorizationService primeFactorizationService;

    @InjectMocks
    FactorizationController underTest;

    @Test
    public void shouldReturnBadRequestOnZeroRequest() {
        // WHEN
        ResponseEntity<List<Long>> response = underTest.factorize(0L);

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnOneOnOneRequest() {
        // WHEN
        ResponseEntity<List<Long>> response = underTest.factorize(1L);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(1L), response.getBody());
    }

    @Test
    public void shouldReturnPrimeFactorizationServiceAnswerOnNormalRequest() {
        // GIVEN
        when(primeFactorizationService.factorizeAppropriateNumber(10L))
                .thenReturn(Arrays.asList(2L, 5L));

        // WHEN
        ResponseEntity<List<Long>> response = underTest.factorize(10L);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(2L, 5L), response.getBody());
    }

}
