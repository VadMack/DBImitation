package com.vadmack.dbimitation;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class TaxiTableImplTest {

    private static final TaxiTable taxiTable = new TaxiTableImpl();

    @BeforeAll
    public static void init() {
        try {
            taxiTable.fromCVS(Path.of("taxi15k.csv"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    void getAverageDistances() {
        Map<Integer, Double> expected = new HashMap<>();
        expected.put(1, 0.2);

        long timerStart = System.currentTimeMillis();
        Map<Integer, Double> result = taxiTable.getAverageDistances(
                LocalDateTime.of(2016, 1, 1, 0, 1, 19),
                LocalDateTime.of(2016, 1, 1, 0, 3,  0));
        log.debug("Request execution time: " + (System.currentTimeMillis() - timerStart));
        System.out.println(result);

        assertTrue(isMapsEqual(expected, result));
    }

    @Test
    void getAverageDistances2() {
        Map<Integer, Double> expected = new HashMap<>();
        expected.put(1, 2.2458);
        expected.put(2, 2.1505);
        expected.put(3, 5.1936);
        expected.put(4, 3.4553);
        expected.put(5, 2.3043);
        expected.put(6, 2.6694);

        long timerStart = System.currentTimeMillis();
        Map<Integer, Double> result = taxiTable.getAverageDistances(
                LocalDateTime.of(2016, 1, 28, 0, 1, 19),
                LocalDateTime.of(2016, 1, 29, 10, 20,  30));
        log.debug("Request execution time: " + (System.currentTimeMillis() - timerStart));
        System.out.println(result);

        assertTrue(isMapsEqual(expected, result));
    }

    @Test
    void getAverageDistances3() {
        Map<Integer, Double> expected = new HashMap<>();
        expected.put(1, 0.9715);
        expected.put(2, 1.1174);
        expected.put(3, 1.0633);
        expected.put(4, 0.652);
        expected.put(5, 0.8711);
        expected.put(6, 0.93);

        long timerStart = System.currentTimeMillis();
        Map<Integer, Double> result = taxiTable.getAverageDistances(
                LocalDateTime.of(2016, 1, 2, 1, 37, 0),
                LocalDateTime.of(2016, 1, 2, 1, 45,  0));;
        log.debug("Request execution time: " + (System.currentTimeMillis() - timerStart));
        System.out.println(result);

        assertTrue(isMapsEqual(expected, result));
    }

    public static boolean isMapsEqual(Map<Integer, Double> expected, Map<Integer, Double> result) {
        Set<Integer> keySet1 = expected.keySet();
        Set<Integer> keySet2 = result.keySet();
        if (keySet1.containsAll(keySet2) && keySet2.containsAll(keySet1)) {
            for (Integer key : keySet1) {
                if (!isEntryEqual(expected.get(key), result.get(key))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static final Double PRECISION = 0.0001;

    private static boolean isEntryEqual(Double d1, Double d2) {
        return Math.abs(d1 - d2) < PRECISION;
    }
}
