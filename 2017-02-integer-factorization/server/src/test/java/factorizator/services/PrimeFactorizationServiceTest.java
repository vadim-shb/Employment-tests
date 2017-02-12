package factorizator.services;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PrimeFactorizationServiceTest {

    PrimeFactorizationService underTest = new PrimeFactorizationService();

    @Test
    public void shouldReturnListOfFactors() {
        // WHEN
        List<Long> result = underTest.factorizeAppropriateNumber(10L);

        // THEN
        assertEquals(Arrays.asList(2L, 5L), result);
    }

    @Test
    public void shouldReturnListOfFactors2() {
        // WHEN
        List<Long> result = underTest.factorizeAppropriateNumber(8L * 3L * 5L * 11L * 29L * 73L);

        // THEN
        assertEquals(Arrays.asList(2L, 2L, 2L, 3L, 5L, 11L, 29L, 73L), result);
    }

}
