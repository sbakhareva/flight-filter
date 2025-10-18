package main.java.com.gridnine.testing.service.impl;

import main.java.com.gridnine.testing.model.Flight;
import main.java.com.gridnine.testing.model.Segment;
import main.java.com.gridnine.testing.service.FlightFilter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Фильтр, исключающий рейсы, у которых время, проведенное на земле, превышает два часа.
 */
public class GroundTimeExceedsTwoHoursFilter implements FlightFilter {

    @Override
    public boolean isValid(Flight flight) {
        List<Segment> segments = flight.getSegments();
        if (segments.size() < 2) {
            return true; // для полета, состоящего из одного сегмента, нет пересадок
        }
        long totalGroundTimeMinutes = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime arrival = segments.get(i).getArrivalDate();
            LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();
            if (nextDeparture.isBefore(arrival)) {
                return false;
            }
            totalGroundTimeMinutes += ChronoUnit.MINUTES.between(arrival, nextDeparture);
        }
        return totalGroundTimeMinutes <= 120;
    }
}
