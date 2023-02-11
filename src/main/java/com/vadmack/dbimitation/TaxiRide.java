package com.vadmack.dbimitation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaxiRide {

    private final double tripDistance;
    private final LocalDateTime tpepPickupDatetime;
    private final LocalDateTime tpepDropoffDatetime;
    private final int passengerCount;
}
