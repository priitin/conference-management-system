package org.conference.model.backoffice;

import lombok.Getter;
import lombok.SneakyThrows;
import org.conference.model.common.Contract;
import org.conference.model.common.Result;
import org.conference.model.common.ResultOf;

@Getter
public class ConferenceTimeRange {
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

    @SneakyThrows
    public static ConferenceTimeRange parse(CharSequence start, CharSequence end) {
        var startDateTime = ConferenceDateTime.parse(start);
        var endDateTime = ConferenceDateTime.parse(end);
        var result = ConferenceTimeRange.create(startDateTime, endDateTime);
        Contract.requiresSuccess(result);

        return result.getValue();
    }

    @Override
    public String toString() {
        return "%s - %s".formatted(this.start, this.end);
    }
}
