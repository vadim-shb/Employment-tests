package factorizator.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Here used simple and pretty inefficient algorithm. It based on counting prime numbers
 * one by one till its enough to decompose any long number. All counted prime numbers saves in
 * primeNumbers concurrent queue.
 * <p>
 * Ways to improve (if somehow we will need it):
 * 1) Use more efficient algorithm
 * 2) Increase limit from Long.MAX_VALUE higher and beyond
 * 3) May be count prime numbers in background thread
 */
@Service
public class PrimeFactorizationService {

    private static final Queue<Long> primeNumbers = new ConcurrentLinkedQueue<>();
    private static long maxCountedPrimeIntegerCandidate = 2L;

    /*
     * Add to counted all the prime numbers less than 1000 (quantity = 168)
     */
    static {
        primeNumbers.addAll(Arrays.asList(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L,
                61L, 67L, 71L, 73L, 79L, 83L, 89L, 97L, 101L, 103L, 107L, 109L, 113L, 127L, 131L, 137L, 139L, 149L, 151L,
                157L, 163L, 167L, 173L, 179L, 181L, 191L, 193L, 197L, 199L, 211L, 223L, 227L, 229L, 233L, 239L, 241L, 251L,
                257L, 263L, 269L, 271L, 277L, 281L, 283L, 293L, 307L, 311L, 313L, 317L, 331L, 337L, 347L, 349L, 353L, 359L,
                367L, 373L, 379L, 383L, 389L, 397L, 401L, 409L, 419L, 421L, 431L, 433L, 439L, 443L, 449L, 457L, 461L, 463L,
                467L, 479L, 487L, 491L, 499L, 503L, 509L, 521L, 523L, 541L, 547L, 557L, 563L, 569L, 571L, 577L, 587L, 593L,
                599L, 601L, 607L, 613L, 617L, 619L, 631L, 641L, 643L, 647L, 653L, 659L, 661L, 673L, 677L, 683L, 691L, 701L,
                709L, 719L, 727L, 733L, 739L, 743L, 751L, 757L, 761L, 769L, 773L, 787L, 797L, 809L, 811L, 821L, 823L, 827L,
                829L, 839L, 853L, 857L, 859L, 863L, 877L, 881L, 883L, 887L, 907L, 911L, 919L, 929L, 937L, 941L, 947L, 953L,
                967L, 971L, 977L, 983L, 991L, 997L));
        maxCountedPrimeIntegerCandidate = 1000L;
    }

    public List<Long> factorizeAppropriateNumber(Long number) {
        countPrimeNumbersIfNecessary(number);
        return factorizeWithPrimeNumbersReady(number);
    }


    private void countPrimeNumbersIfNecessary(Long numberToFactorize) {
        final long maxCheckedDivider = Math.round(Math.sqrt(numberToFactorize));
        if (maxCheckedDivider > maxCountedPrimeIntegerCandidate) {
            countPrimeNumbersFor(maxCheckedDivider);
        }
    }

    private List<Long> factorizeWithPrimeNumbersReady(Long numberToFactorize) {
        List<Long> result = new ArrayList<>();
        long numberAfterDivisions = numberToFactorize;
        for (Long primeNumber : primeNumbers) {
            if (primeNumber > numberAfterDivisions) {
                break;
            }
            while (numberAfterDivisions % primeNumber == 0) {
                result.add(primeNumber);
                numberAfterDivisions = numberAfterDivisions / primeNumber;
            }
        }
        if (numberAfterDivisions > 1) {
            result.add(numberAfterDivisions);
        }
        return result;
    }

    /**
     * Add to counted queue all the prime numbers less than "border"
     */
    private static synchronized void countPrimeNumbersFor(final long border) {
        if (border > maxCountedPrimeIntegerCandidate) {
            for (long i = oddStartStep(); i < border; i += 2) {
                if (isNextPrime(i)) {
                    primeNumbers.add(i);
                }
            }
            maxCountedPrimeIntegerCandidate = border;
        }
    }

    private static long oddStartStep() {
        if (maxCountedPrimeIntegerCandidate % 2 == 0) {
            return maxCountedPrimeIntegerCandidate + 1;
        }
        return maxCountedPrimeIntegerCandidate;
    }

    /**
     * Checks if income value is next prime number in counted list of prime numbers.
     * It is assumed that this method runs for every candidate on the next prime number from
     * lower to higher, without gaps. It is also assumed that if this method returns true, than
     * prime number goes to primeNumbers list.
     */
    private static boolean isNextPrime(final long number) {
        final long maxCheckedDivider = Math.round(Math.sqrt(number));
        for (Long primeNumber : primeNumbers) {
            if (primeNumber > maxCheckedDivider) {
                break;
            }
            if (number % primeNumber == 0) {
                return false;
            }
        }
        return true;
    }

}
