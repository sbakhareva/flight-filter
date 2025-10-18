package main.java.com.gridnine.testing.service;

import main.java.com.gridnine.testing.model.Flight;

/**
 * Интерфейс для фильтров рейсов.
 * Каждый фильтр определяет правило, где isValid возвращает true,
 * если рейс соответствует фильтру (не должен быть исключен).
 */
public interface FlightFilter {

    boolean isValid(Flight flight);
}