package main.java.com.gridnine.testing.service.impl;

import main.java.com.gridnine.testing.model.Flight;
import main.java.com.gridnine.testing.service.FlightFilter;

/**
 * Фильтр, исключающий рейсы, где прибытие предшествует отправлению.
 */
public class ArrivalBeforeDepartureFilter implements FlightFilter {

    @Override
    public boolean isValid(Flight flight) {
        return flight.getSegments().stream()
                .allMatch(segment ->
                        !segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }
}
