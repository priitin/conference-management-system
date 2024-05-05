package org.conference.model.backoffice;

import org.conference.model.common.ConferenceTimeRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConferenceTests {

    @Test
    public void creating_a_valid_conference_should_succeed() {
        var timeRange = ConferenceTimeRange.parse("2024-01-01T12:00", "2024-01-01T14:00");
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.READY, "Location", 10).getValue();

        var conferenceResult = Conference.create(1, timeRange, room);

        Assertions.assertTrue(conferenceResult.isSuccess());
    }

    @Test
    public void creating_a_conference_for_a_room_that_is_under_construction_should_fail() {
        var timeRange = ConferenceTimeRange.parse("2024-01-01T12:00", "2024-01-01T14:00");
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.UNDER_CONSTRUCTION, "Location", 10).getValue();

        var conferenceResult = Conference.create(1, timeRange, room);

        Assertions.assertTrue(conferenceResult.isFailure());
        Assertions.assertEquals("Conference room 'TestRoom' is under construction", conferenceResult.getErrorMessage());
    }

    @Test
    public void copying_a_conference_should_not_reference_its_original() {
        var timeRange = ConferenceTimeRange.parse("2024-01-01T12:00", "2024-01-01T14:00");
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.READY, "Location", 10).getValue();
        var original = Conference.create(1, timeRange, room).getValue();

        var copy = original.copy();
        copy.changeTime(ConferenceTimeRange.parse("2024-01-01T15:00", "2024-01-01T16:00"));

        Assertions.assertNotSame(copy, original);
        Assertions.assertEquals(original.getTimeRange().toString(), "2024-01-01T12:00+00:00 - 2024-01-01T14:00+00:00");
        Assertions.assertEquals(copy.getTimeRange().toString(), "2024-01-01T15:00+00:00 - 2024-01-01T16:00+00:00");
    }
}
