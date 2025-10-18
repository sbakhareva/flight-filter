package test.com.gridnine.testing;

import main.java.com.gridnine.testing.model.Flight;
import main.java.com.gridnine.testing.model.FlightBuilder;
import main.java.com.gridnine.testing.service.FlightFilter;
import main.java.com.gridnine.testing.service.impl.ArrivalBeforeDepartureFilter;
import main.java.com.gridnine.testing.service.impl.DepartureBeforeNowFilter;
import main.java.com.gridnine.testing.service.impl.GroundTimeExceedsTwoHoursFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlightFiltersTest {
    private List<Flight> flights;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        flights = FlightBuilder.createFlights();
    }

    @Test
    void testDepartureBeforeNowFilter() {
        System.out.println("\nТест для фильтра по отправлению раньше текущего времени");
        FlightFilter filter = new DepartureBeforeNowFilter(now);
        // Рейс №3: рейс отправляется до текущей даты
        assertFalse(filter.isValid(flights.get(2)));
        System.out.println("Для рейса №3 время отправления раньше текущего.");
        // Обычный полет
        assertTrue(filter.isValid(flights.get(0)));
    }

    @Test
    void testArrivalBeforeDepartureFilter() {
        System.out.println("\nТест для фильтра по прибытию раньше отправления.");
        FlightFilter filter = new ArrivalBeforeDepartureFilter();
        // Рейс №4: время прибытия раньше времени отправления
        assertFalse(filter.isValid(flights.get(3)));
        System.out.println("Для рейса №4 время прибытия раньше времени отправления.");
        // Обычный полет
        assertTrue(filter.isValid(flights.get(0)));
    }

    @Test
    void testGroundTimeExceedsTwoHoursFilter() {
        System.out.println("\nТест для фильтра по суммарному времени пересадок");
        FlightFilter filter = new GroundTimeExceedsTwoHoursFilter();
        // Рейс №5: пересадки 3 часа > 2
        assertFalse(filter.isValid(flights.get(4)));
        System.out.println("Для рейса №5 суммарное время на земле составляет 3ч.");
        // Рейс №6: пересадки 1ч + 2ч = 3ч > 2
        assertFalse(filter.isValid(flights.get(5)));
        System.out.println("Для рейса №6 суммарное время двух пересадок составляет 3ч.");
        // Обычный рейс с несколькими пересадками: время на земле 1ч <=2
        assertTrue(filter.isValid(flights.get(1)));
        System.out.println("Для обычного рейса с двумя пересадками суммарное время на земле не превышает 2ч.");
        // Рейс без пересадок
        assertTrue(filter.isValid(flights.get(0)));
        System.out.println("Для рейса без пересадок всегда возвращается true.");
    }
}