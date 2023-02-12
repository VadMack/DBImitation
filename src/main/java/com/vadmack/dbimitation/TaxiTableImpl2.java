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
import java.util.*;

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
        int indexOfMaxAllowed = findIndexOfLastSuitableValue(end);
        if (indexOfMaxAllowed == -1) {
            return Collections.emptyMap();
        }

        Map<Integer, Double> result = new HashMap<>();
        Map<Integer, Integer> counter = new HashMap<>();

        for (TaxiRide ride: rides.subList(0, indexOfMaxAllowed)) {
            if (!ride.getTpepPickupDatetime().isBefore(start)) {
                int pCount = ride.getPassengerCount();
                double distance = ride.getTripDistance();
                result.merge(pCount, distance, Double::sum);
                counter.merge(pCount, 1, Integer::sum);
            }
        }

        for (int key : result.keySet()) {
            result.merge(key, (double) counter.get(key), (sum, number) -> (sum/number));
        }

        return result;
    }

    private int findIndexOfLastSuitableValue(LocalDateTime target) {
        int start = 0;
        int end = rides.size() - 1;

        int ans = -1;
        while (start <= end) {
            int mid = (start + end) / 2;

            if (!rides.get(mid).getTpepDropoffDatetime().isAfter(target)) {
                start = mid + 1;
            }

            else {
                ans = mid;
                end = mid - 1;
            }
        }
        return ans;
    }
}
