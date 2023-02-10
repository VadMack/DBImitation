package com.vadmack.dbimitation;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
public class Main {

    public static void main(String[] args) {
        TaxiTable taxiTable = new TaxiTableImpl();

        try {
            taxiTable.fromCVS(Path.of("taxi15k.csv"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        long timerStart = System.currentTimeMillis();
        Map<Integer, Double> result = taxiTable.getAverageDistances(
                LocalDateTime.of(2016, 1, 1, 0, 0, 0),
                LocalDateTime.of(2016, 1, 1, 0, 0,  1));
        log.debug("Request execution time: " + (System.currentTimeMillis() - timerStart));
        System.out.println(result);
    }
}
