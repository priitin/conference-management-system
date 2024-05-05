package org.conference.model.backoffice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ConferenceTimeRangeTests {

    @Test
    public void creating_a_valid_time_range_should_succeed() {
        var start = ConferenceDateTime.parse("2024-01-01T12:00");
        var end = ConferenceDateTime.parse("2024-01-01T12:01");

        var timeRangeResult = ConferenceTimeRange.create(start, end);

        Assertions.assertTrue(timeRangeResult.isSuccess());
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01T12:01,2024-01-01T12:00",
            "2024-01-01T12:00,2024-01-01T12:00",
    })
    public void start_time_should_be_before_its_end_time(String startStr, String endStr) {
        var start = ConferenceDateTime.parse(startStr);
        var end = ConferenceDateTime.parse(endStr);

        var timeRangeResult = ConferenceTimeRange.create(start, end);

        Assertions.assertTrue(timeRangeResult.isFailure());
        Assertions.assertEquals("Conference start time has to be before its end time", timeRangeResult.getErrorMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01T11:00,2024-01-01T12:00",
            "2024-01-01T11:00,2024-01-01T12:01",
            "2024-01-01T13:59,2024-01-01T15:00",
            "2024-01-01T14:00,2024-01-01T15:00",

            "2024-01-01T11:59,2024-01-01T14:01",
            "2024-01-01T12:01,2024-01-01T13:59",
    })
    public void two_ranges_should_intersect_if_their_times_match(String startStr, String endStr) {
        var first = ConferenceTimeRange.parse("2024-01-01T12:00", "2024-01-01T14:00");
        var second = ConferenceTimeRange.parse(startStr, endStr);

        var areIntersecting = first.intersects(second);

        Assertions.assertTrue(areIntersecting);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01T11:00,2024-01-01T11:59",
            "2024-01-01T14:01,2024-01-01T15:00",
    })
    public void two_ranges_should_not_intersect_if_their_times_do_not_match(String startStr, String endStr) {
        var first = ConferenceTimeRange.parse("2024-01-01T12:00", "2024-01-01T14:00");
        var second = ConferenceTimeRange.parse(startStr, endStr);

        var areIntersecting = first.intersects(second);

        Assertions.assertFalse(areIntersecting);
    }
}
