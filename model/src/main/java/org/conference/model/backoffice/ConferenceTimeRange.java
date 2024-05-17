package org.conference.model.backoffice;

import lombok.Getter;
import lombok.SneakyThrows;
import org.conference.model.common.Contract;
import org.conference.model.common.Result;
import org.conference.model.common.ResultOf;
import org.conference.model.common.ValueObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

@Getter
public class ConferenceTimeRange extends ValueObject {
    private final ConferenceDateTime start;
    private final ConferenceDateTime end;

    private ConferenceTimeRange(ConferenceDateTime start, ConferenceDateTime end) {
        this.start = start;
        this.end = end;
    }

    public static ResultOf<ConferenceTimeRange> create(ConferenceDateTime start, ConferenceDateTime end) {
        if (start.isAfterOrEqual(end))
            return Result.failure("Conference start time has to be before its end time");

        return Result.succeed(new ConferenceTimeRange(start, end));
    }

    public static ResultOf<ConferenceTimeRange> create(OffsetDateTime start, OffsetDateTime end) {
        var startTime = ConferenceDateTime.of(start);
        var endTime = ConferenceDateTime.of(end);
        return ConferenceTimeRange.create(startTime, endTime);
    }

    @SneakyThrows
    public static ConferenceTimeRange parse(CharSequence start, CharSequence end) {
        var startDateTime = ConferenceDateTime.parse(start);
        var endDateTime = ConferenceDateTime.parse(end);
        var result = ConferenceTimeRange.create(startDateTime, endDateTime);
        Contract.requiresSuccess(result);

        return result.getValue();
    }

    public boolean intersects(ConferenceTimeRange other) {
        return this.start.isBeforeOrEqual(other.end) && this.end.isAfterOrEqual(other.start);
    }

    @Override
    public String toString() {
        return "%s - %s".formatted(this.start, this.end);
    }

    @Override
    protected Stream<Object> getEqualityComponents() {
        return Stream.of(start, end);
    }
}
