package org.conference.model.backoffice;

import org.conference.model.common.ContractException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ConferenceDateTimeTests {

    @ParameterizedTest
    @CsvSource({
            "2024-01-01T12:00,2024-01-01T12:00+00:00",
            "2024-01-01T12:00:00.000,2024-01-01T12:00+00:00",
            "2024-01-01T12:00:59.9999,2024-01-01T12:00+00:00",
    })
    public void conference_date_time_should_be_parsed_from_ISO_8601_string_without_offset(String value, String expectedDateTime) {
        var dateTime = ConferenceDateTime.parse(value);

        var dateTimeString = dateTime.toString();

        Assertions.assertEquals(expectedDateTime, dateTimeString);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01T12:00+00:00,2024-01-01T12:00+00:00",
            "2024-01-01T12:00Z,2024-01-01T12:00+00:00",
            "2024-01-01T12:00+01:30,2024-01-01T12:00+01:30",
            "2024-01-01T12:00-01:30,2024-01-01T12:00-01:30",
            "2024-01-01T12:00:59.999-01:30,2024-01-01T12:00-01:30",
            "2024-01-01T12:00+01:30:59,2024-01-01T12:00+01:30",
    })
    public void conference_date_time_should_be_parsed_from_ISO_8601_string_with_offset(String value, String expectedDateTime) {
        var dateTime = ConferenceDateTime.parse(value);

        var dateTimeString = dateTime.toString();

        Assertions.assertEquals(expectedDateTime, dateTimeString);
    }

    /**
     * Some valid ISO 8601 strings cannot be parsed by the {@link java.time} package.
     */
    @ParameterizedTest
    @CsvSource({
            "20240101T1200",
            "2024-01-01 12:00",
            "2024-01-01T12:00+0000",
            "2024-01-01T12:00+00",
    })
    public void conference_date_time_cannot_be_parsed_from_known_ISO_8601_strings(String value) {
        Executable executable = () -> ConferenceDateTime.parse(value);

        var ex = Assertions.assertThrows(ContractException.class, executable);
        Assertions.assertTrue(ex.getMessage().contains("into ConferenceDateTime"));
    }
}
