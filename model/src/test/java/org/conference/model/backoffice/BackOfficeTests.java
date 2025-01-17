package org.conference.model.backoffice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BackOfficeTests {

    @Test
    public void adding_a_conference_without_existing_conferences_should_succeed() {
        var timeRange = ConferenceTimeRange.parse("2024-01-01T12:00", "2024-01-01T14:00");
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.READY, "Location", 10).getValue();
        var conference = Conference.create(1, timeRange, room).getValue();
        var backOffice = new BackOffice();

        backOffice.addConference(conference);

        Assertions.assertEquals(1, backOffice.getConferences().size());
    }

    @Test
    public void updating_an_existing_conference_should_update_its_data() {
        var existingConference = Conference.create(1,
                ConferenceTimeRange.parse("2024-01-01T12:00", "2024-01-01T14:00"),
                ConferenceRoom.create(1, "TestRoom1", ConferenceRoomStatus.READY, "Location", 10).getValue()
        ).getValue();
        var backOffice = new BackOffice(existingConference);

        var conference = existingConference.copy();
        conference.changeTime(ConferenceTimeRange.parse("2024-01-01T10:00", "2024-01-01T18:00"));
        backOffice.updateConference(conference);

        Assertions.assertEquals(1, backOffice.getConferences().size());
        Assertions.assertEquals(
                "2024-01-01T10:00+00:00 - 2024-01-01T18:00+00:00",
                backOffice.getConferences().getFirst().getTimeRange().toString());
    }

    @Test
    public void adding_a_conference_with_same_time_as_existing_but_different_room_should_succeed() {
        var existingConference = Conference.create(1,
                ConferenceTimeRange.parse("2024-01-01T12:00", "2024-01-01T14:00"),
                ConferenceRoom.create(1, "TestRoom1", ConferenceRoomStatus.READY, "Location", 10).getValue()
        ).getValue();
        var backOffice = new BackOffice(existingConference);

        var conference = Conference.create(2,
                existingConference.getTimeRange(),
                ConferenceRoom.create(2, "TestRoom2", ConferenceRoomStatus.READY, "Location", 10).getValue()
        ).getValue();
        backOffice.addConference(conference);

        Assertions.assertEquals(2, backOffice.getConferences().size());
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01T12:01,2024-01-01T13:59",
            "2024-01-01T10:00,2024-01-01T12:00",
            "2024-01-01T10:00,2024-01-01T12:01",
            "2024-01-01T14:00,2024-01-01T16:00",
            "2024-01-01T13:59,2024-01-01T16:00",
    })
    public void adding_a_conference_with_conflicting_time_and_room_as_existing_should_fail(
            String start, String end) {
        var existingConference = Conference.create(1,
                ConferenceTimeRange.parse("2024-01-01T12:00", "2024-01-01T14:00"),
                ConferenceRoom.create(1, "TestRoom1", ConferenceRoomStatus.READY, "Location", 10).getValue()
        ).getValue();
        var backOffice = new BackOffice(existingConference);

        var conference = Conference.create(2,
                ConferenceTimeRange.parse(start, end),
                existingConference.getRoom()
        ).getValue();
        var result = backOffice.hasConflicts(conference);

        Assertions.assertTrue(result.isFailure());
        Assertions.assertTrue(result.getErrorMessage().startsWith("There is already a conference in the room"));
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01T12:01,2024-01-01T13:59",
            "2024-01-01T10:00,2024-01-01T12:00",
            "2024-01-01T10:00,2024-01-01T12:01",
            "2024-01-01T14:00,2024-01-01T16:00",
            "2024-01-01T13:59,2024-01-01T16:00",
    })
    public void updating_a_conference_with_conflicting_time_and_room_as_existing_should_fail(
            String start, String end) {
        var existingConference = Conference.create(1,
                ConferenceTimeRange.parse("2024-01-01T12:00", "2024-01-01T14:00"),
                ConferenceRoom.create(1, "TestRoom1", ConferenceRoomStatus.READY, "Location", 10).getValue()
        ).getValue();
        var conferenceToUpdate = Conference.create(2,
                ConferenceTimeRange.parse("2024-01-01T08:00", "2024-01-01T09:00"),
                ConferenceRoom.create(1, "TestRoom1", ConferenceRoomStatus.READY, "Location", 10).getValue()
        ).getValue();
        var backOffice = new BackOffice(existingConference, conferenceToUpdate);

        var conference = conferenceToUpdate.copy();
        conference.changeTime(ConferenceTimeRange.parse(start, end));
        var result = backOffice.hasConflicts(conference);

        Assertions.assertTrue(result.isFailure());
        Assertions.assertTrue(result.getErrorMessage().startsWith("There is already a conference in the room"));
    }
}
