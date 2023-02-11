package com.vadmack.dbimitation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaxiTableImpl2 implements TaxiTable {
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    private final List<TaxiRide> rides = new ArrayList<>();

    @Getter
    @AllArgsConstructor
    private enum Headers {
        TRIP_DISTANCE("trip_distance"),
        PICKUP("tpep_pickup_datetime"),
        DROPOFF("tpep_dropoff_datetime"),
        PASSENGER_COUNT("passenger_count");

        private final String fieldName;
    }

    @Override
    public void fromCVS(Path path) throws IOException {
        Reader in = new FileReader(path.toFile());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        for (CSVRecord rec : records) {
            TaxiRide ride = new TaxiRide(Double.parseDouble(rec.get(Headers.TRIP_DISTANCE.getFieldName())),
                    LocalDateTime.parse(rec.get(Headers.PICKUP.getFieldName()), formatter),
                    LocalDateTime.parse(rec.get(Headers.DROPOFF.getFieldName()), formatter),
                    Integer.parseInt(rec.get(Headers.PASSENGER_COUNT.getFieldName())));
            rides.add(ride);
        }

        rides.sort(Comparator.comparing(TaxiRide::getTpepDropoffDatetime));
    }

    @Override
    public Map<Integer, Double> getAverageDistances(LocalDateTime start, LocalDateTime end) {
        List<TaxiRide> pool = new ArrayList<>();
        for (TaxiRide ride : rides) {
            //System.out.println(ride.getTpepPickupDatetime() + "\t" + ride.getTpepDropoffDatetime());

            if (ride.getTpepDropoffDatetime().isAfter(end)) {
                break;
            } else {
                if (!ride.getTpepPickupDatetime().isBefore(start)) {
                    pool.add(ride);
                }
            }
        }

        return pool.stream().collect(Collectors.groupingBy(TaxiRide::getPassengerCount,
                Collectors.averagingDouble(TaxiRide::getTripDistance)));
    }
}