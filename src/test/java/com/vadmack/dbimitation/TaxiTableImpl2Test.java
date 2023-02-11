package com.vadmack.dbimitation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.vadmack.dbimitation.TaxiTableImplTest.isMapsEqual;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class TaxiTableImpl2Test {

    private static final TaxiTable taxiTable = new TaxiTableImpl2();

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
}