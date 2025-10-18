package main.java.com.gridnine.testing;

import main.java.com.gridnine.testing.model.Flight;
import main.java.com.gridnine.testing.model.FlightBuilder;
import main.java.com.gridnine.testing.service.FlightFilter;
import main.java.com.gridnine.testing.service.impl.ArrivalBeforeDepartureFilter;
import main.java.com.gridnine.testing.service.impl.DepartureBeforeNowFilter;
import main.java.com.gridnine.testing.service.impl.GroundTimeExceedsTwoHoursFilter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();
        LocalDateTime now = LocalDateTime.now();

        // Все полёты
        System.out.println("\nВсе полёты:");
        printFlights(flights);

        // Правило №1: Вылет раньше текущего времени
        FlightFilter filter1 = new DepartureBeforeNowFilter(now);
        List<Flight> excluded1 = excludeFlights(flights, filter1);
        System.out.println("\nРейсы исключены из-за вылета раньше текущего времени:");
        printFlights(excluded1);

        // Правило №2: Время прилета раньше времени вылета
        FlightFilter filter2 = new ArrivalBeforeDepartureFilter();
        List<Flight> excluded2 = excludeFlights(flights, filter2);
        System.out.println("\nРейсы исключены из-за времени прибытия раньше времени вылета:");
        printFlights(excluded2);

        // Правило №3: Рейсы со временем прибывания на земле больше 2 часов
        FlightFilter filter3 = new GroundTimeExceedsTwoHoursFilter();
        List<Flight> excluded3 = excludeFlights(flights, filter3);
        System.out.println("\nРейсы исключены, поскольку общее время, проведенное на земле, превышает 2 часа.:");
        printFlights(excluded3);
    }

    private static List<Flight> excludeFlights(List<Flight> flights, FlightFilter filter) {
        return flights.stream()
                .filter(flight -> !filter.isValid(flight))
                .collect(Collectors.toList());
    }

    private static void printFlights(List<Flight> flights) {
        if (flights.isEmpty()) {
            System.out.println("No flights excluded.");
        } else {
            flights.forEach(System.out::println);
        }
    }
}
