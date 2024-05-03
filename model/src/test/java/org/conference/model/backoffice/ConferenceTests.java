package org.conference.model.backoffice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class ConferenceTests {

    @Test
    public void creating_a_valid_conference_should_succeed() {
        var start = ZonedDateTime.of(2024, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone());
        var end = ZonedDateTime.of(2024, 1, 1, 14, 0, 0, 0, ZonedDateTime.now().getZone());
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.READY, "Location", 10).getValue();

        var conferenceResult = Conference.create(1, start, end, room);

        Assertions.assertTrue(conferenceResult.isSuccess());
    }

    @Test
    public void conference_start_time_should_be_before_its_end_time() {
        var start = ZonedDateTime.of(2024, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone());
        var end = ZonedDateTime.of(2024, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone());
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.READY, "Location", 10).getValue();

        var conferenceResult = Conference.create(1, start, end, room);

        Assertions.assertTrue(conferenceResult.isFailure());
        Assertions.assertEquals("Conference start time has to be before its end time", conferenceResult.getErrorMessage());
    }

    @Test
    public void creating_a_conference_for_a_room_that_is_under_construction_should_fail() {
        var start = ZonedDateTime.of(2024, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone());
        var end = ZonedDateTime.of(2024, 1, 1, 14, 0, 0, 0, ZonedDateTime.now().getZone());
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.UNDER_CONSTRUCTION, "Location", 10).getValue();

        var conferenceResult = Conference.create(1, start, end, room);

        Assertions.assertTrue(conferenceResult.isFailure());
        Assertions.assertEquals("Conference room 'TestRoom' is under construction", conferenceResult.getErrorMessage());
    }
}
