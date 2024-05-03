package org.conference.model.common;

import lombok.Getter;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConferenceDateTime {
    @Getter
    private final OffsetDateTime value;

    private ConferenceDateTime(int year, int month, int day, int hour, int minute, ZoneOffset offset) {
        this.value = OffsetDateTime.of(year, month, day, hour, minute, 0, 0, offset);
    }

    /**
     * Creates a ConferenceDateTime with an offset from UTC.
     */
    public static ConferenceDateTime of(int year, int month, int day, int hour, int minute, ZoneOffset offset) {
        return new ConferenceDateTime(year, month, day, hour, minute, offset);
    }

    /**
     * Creates a ConferenceDateTime with UTC +00:00 as the offset.
     */
    public static ConferenceDateTime of(int year, int month, int day, int hour, int minute) {
        return new ConferenceDateTime(year, month, day, hour, minute, ZoneOffset.UTC);
    }

    @SneakyThrows
    public static ConferenceDateTime parse(CharSequence value) {
        try {
            var time = OffsetDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
            return ConferenceDateTime.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth(),
                    time.getHour(), time.getMinute(), time.getOffset());
        } catch (DateTimeParseException _) {
        }

        try {
            var time = LocalDateTime.parse(value);
            return ConferenceDateTime.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth(),
                    time.getHour(), time.getMinute());
        } catch (DateTimeParseException ex) {
            throw new ContractException("Cannot parse '%s' into ConferenceDateTime".formatted(value), ex);
        }
    }

    public boolean isEqual(ConferenceDateTime other) {
        return this.value.isEqual(other.getValue());
    }

    public boolean isBefore(ConferenceDateTime other) {
        return this.value.isBefore(other.getValue());
    }

    public boolean isAfter(ConferenceDateTime other) {
        return this.value.isAfter(other.getValue());
    }

    /**
     * Example: 2024-01-01T13:00+00:00
     */
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmxxx");

    @Override
    public String toString() {
        return OUTPUT_FORMATTER.format(this.value);
    }
}
