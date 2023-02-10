package com.vadmack.dbimitation;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

interface TaxiTable {
    void fromCVS(Path path) throws IOException;
    Map<Integer, Double> getAverageDistances(LocalDateTime start, LocalDateTime end);
}