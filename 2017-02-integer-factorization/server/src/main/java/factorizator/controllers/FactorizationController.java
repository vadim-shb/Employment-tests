package factorizator.controllers;

import factorizator.services.PrimeFactorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@RestController
public class FactorizationController {

    private static final ResponseEntity<List<Long>> badResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    private static final ResponseEntity<List<Long>> oneResponse = ResponseEntity.ok(Collections.singletonList(1L));

    @Inject
    private PrimeFactorizationService primeFactorizationService;

    @GetMapping("/api/prime-factorize/{number}")
    public ResponseEntity<List<Long>> factorize(@PathVariable Long number) {
        if (number < 1) {
            return badResponse;
        }
        if (number.equals(1L)) {
            return oneResponse;
        }
        return ResponseEntity.ok(primeFactorizationService.factorizeAppropriateNumber(number));
    }

}
