package main.java.com.gridnine.testing.service.impl;

import main.java.com.gridnine.testing.model.Flight;
import main.java.com.gridnine.testing.service.FlightFilter;

import java.time.LocalDateTime;

/**
 * Фильтр, исключающий рейсы, отправляющиеся раньше текущего времени
 */
public class DepartureBeforeNowFilter implements FlightFilter {
    private final LocalDateTime now;

    DepartureBeforeNowFilter() {
        this.now = LocalDateTime.now();
    }

    // For testing, allow injecting now
    public DepartureBeforeNowFilter(LocalDateTime now) {
        this.now = now;
    }

    @Override
    public boolean isValid(Flight flight) {
        return flight.getSegments().stream()
                .allMatch(segment -> !segment.getDepartureDate().isBefore(now));
    }
}