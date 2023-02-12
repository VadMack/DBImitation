package com.vadmack.dbimitation;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
public class Main {

    public static void main(String[] args) {
        Path taxi15k = Path.of("taxi15k.csv");

        initAndExecute(new TaxiTableImpl(), 10000, taxi15k,
                LocalDateTime.of(2016, 1, 1, 0, 1, 19),
                LocalDateTime.of(2016, 1, 1, 0, 3,  0));
        initAndExecute(new TaxiTableImpl2(), 10000, taxi15k,
                LocalDateTime.of(2016, 1, 1, 0, 1, 19),
                LocalDateTime.of(2016, 1, 1, 0, 3,  0));
        System.out.println();

        initAndExecute(new TaxiTableImpl(), 10000, taxi15k,
                LocalDateTime.of(2016, 1, 1, 0, 1, 19),
                LocalDateTime.of(2016, 1, 2, 0, 0,  0));
        initAndExecute(new TaxiTableImpl2(), 10000, taxi15k,
                LocalDateTime.of(2016, 1, 1, 0, 1, 19),
                LocalDateTime.of(2016, 1, 2, 0, 0,  0));
        System.out.println();

        initAndExecute(new TaxiTableImpl(), 10000, taxi15k,
                LocalDateTime.of(2016, 1, 2, 1, 37, 0),
                LocalDateTime.of(2016, 1, 2, 1, 45,  0));
        initAndExecute(new TaxiTableImpl2(), 10000, taxi15k,
                LocalDateTime.of(2016, 1, 2, 1, 37, 0),
                LocalDateTime.of(2016, 1, 2, 1, 45,  0));
        System.out.println();

        initAndExecute(new TaxiTableImpl(), 10000, taxi15k,
                LocalDateTime.of(2016, 1, 1, 0, 1, 19),
                LocalDateTime.of(2016, 1, 29, 10, 20,  30));
        initAndExecute(new TaxiTableImpl2(), 10000, taxi15k,
                LocalDateTime.of(2016, 1, 1, 0, 1, 19),
                LocalDateTime.of(2016, 1, 29, 10, 20,  30));
        System.out.println();

        initAndExecute(new TaxiTableImpl(), 10000, taxi15k,
                LocalDateTime.of(2016, 1, 28, 0, 1, 19),
                LocalDateTime.of(2016, 1, 29, 10, 20,  30));
        initAndExecute(new TaxiTableImpl2(), 10000, taxi15k,
                LocalDateTime.of(2016, 1, 28, 0, 1, 19),
                LocalDateTime.of(2016, 1, 29, 10, 20,  30));
        System.out.println();

    }


    private static void initAndExecute(TaxiTable table, int iterations, Path path,
                                       LocalDateTime start, LocalDateTime end) {

        try {
            table.fromCVS(path);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        long timer = 0;
        for (int i = 0; i < iterations; i++) {
            long timerStart = System.currentTimeMillis();
            Map<Integer, Double> result  = table.getAverageDistances(start, end);
            timer += System.currentTimeMillis() - timerStart;
            //System.out.println(result);
        }
        log.debug(table.getClass().getSimpleName() + " " + iterations + " requests execution time: " + timer);
    }
}
