package org.conference.model.backoffice;

import org.conference.model.common.ConferenceDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ConferenceTests {

    @Test
    public void creating_a_valid_conference_should_succeed() {
        var start = ConferenceDateTime.parse("2024-01-01T12:00");
        var end = ConferenceDateTime.parse("2024-01-01T14:00");
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.READY, "Location", 10).getValue();

        var conferenceResult = Conference.create(1, start, end, room);

        Assertions.assertTrue(conferenceResult.isSuccess());
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01T12:01,2024-01-01T12:00",
            "2024-01-01T12:00,2024-01-01T12:00",
    })
    public void conference_start_time_should_be_before_its_end_time(String startStr, String endStr) {
        var start = ConferenceDateTime.parse(startStr);
        var end = ConferenceDateTime.parse(endStr);
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.READY, "Location", 10).getValue();

        var conferenceResult = Conference.create(1, start, end, room);

        Assertions.assertTrue(conferenceResult.isFailure());
        Assertions.assertEquals("Conference start time has to be before its end time", conferenceResult.getErrorMessage());
    }

    @Test
    public void creating_a_conference_for_a_room_that_is_under_construction_should_fail() {
        var start = ConferenceDateTime.parse("2024-01-01T12:00");
        var end = ConferenceDateTime.parse("2024-01-01T14:00");
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.UNDER_CONSTRUCTION, "Location", 10).getValue();

        var conferenceResult = Conference.create(1, start, end, room);

        Assertions.assertTrue(conferenceResult.isFailure());
        Assertions.assertEquals("Conference room 'TestRoom' is under construction", conferenceResult.getErrorMessage());
    }

    @Test
    public void copying_a_conference_should_not_reference_its_original() {
        var start = ConferenceDateTime.parse("2024-01-01T12:00");
        var end = ConferenceDateTime.parse("2024-01-01T14:00");
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.READY, "Location", 10).getValue();
        var original = Conference.create(1, start, end, room).getValue();

        var copy = original.copy();
        copy.changeTime(ConferenceDateTime.parse("2024-01-01T15:00"), ConferenceDateTime.parse("2024-01-01T16:00"));

        Assertions.assertNotSame(copy, original);
        Assertions.assertEquals(original.getStart().toString(), "2024-01-01T12:00+00:00");
        Assertions.assertEquals(original.getEnd().toString(), "2024-01-01T14:00+00:00");
        Assertions.assertEquals(copy.getStart().toString(), "2024-01-01T15:00+00:00");
        Assertions.assertEquals(copy.getEnd().toString(), "2024-01-01T16:00+00:00");
    }
}
