package com.vadmack.dbimitation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaxiRide {

    private double tripDistance;
    private LocalDateTime tpepPickupDatetime;
    private LocalDateTime tpepDropoffDatetime;
    private int passengerCount;
}
