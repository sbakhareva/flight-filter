package main.java.com.gridnine.testing.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, описывающий один полет, состоящий из одного или нескольких сегментов.
 */
public class Flight {

    private final List<Segment> segments;

    Flight(final List<Segment> segs) {
        segments = segs;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }

}
