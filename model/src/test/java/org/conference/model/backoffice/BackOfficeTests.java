package org.conference.model.backoffice;

import org.conference.model.common.ConferenceDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BackOfficeTests {

    @Test
    public void adding_a_conference_without_existing_conferences_should_succeed() {
        var start = ConferenceDateTime.parse("2024-01-01T12:00");
        var end = ConferenceDateTime.parse("2024-01-01T14:00");
        var room = ConferenceRoom.create(1, "TestRoom", ConferenceRoomStatus.READY, "Location", 10).getValue();
        var conference = Conference.create(1, start, end, room).getValue();
        var backOffice = new BackOffice();

        var result = backOffice.addConference(conference);

        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(1, backOffice.getConferences().size());
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01T12:01,2024-01-01T13:59",
            "2024-01-01T10:00,2024-01-01T12:00",
            "2024-01-01T10:00,2024-01-01T12:01",
            "2024-01-01T14:00,2024-01-01T16:00",
            "2024-01-01T13:59,2024-01-01T16:00",
    })
    public void adding_a_conference_with_a_start_and_end_time_that_conflicts_with_existing_conferences_should_fail(
            String start, String end) {
        var existingConference = Conference.create(1,
                ConferenceDateTime.parse("2024-01-01T12:00"),
                ConferenceDateTime.parse("2024-01-01T14:00"),
                ConferenceRoom.create(1, "TestRoom1", ConferenceRoomStatus.READY, "Location", 10).getValue()
        ).getValue();
        var backOffice = new BackOffice(existingConference);
        var conference = Conference.create(2,
                ConferenceDateTime.parse(start),
                ConferenceDateTime.parse(end),
                ConferenceRoom.create(2, "TestRoom2", ConferenceRoomStatus.READY, "Location", 10).getValue()
        ).getValue();

        var result = backOffice.addConference(conference);

        Assertions.assertTrue(result.isFailure());
        Assertions.assertTrue(result.getErrorMessage().startsWith("There is already a conference between"));
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01T12:01,2024-01-01T13:59",
            "2024-01-01T10:00,2024-01-01T12:00",
            "2024-01-01T10:00,2024-01-01T12:01",
            "2024-01-01T14:00,2024-01-01T16:00",
            "2024-01-01T13:59,2024-01-01T16:00",
    })
    public void updating_a_conference_with_a_start_and_end_time_that_conflicts_with_existing_conferences_should_fail(
            String start, String end) {
        var existingConference = Conference.create(1,
                ConferenceDateTime.parse("2024-01-01T12:00"),
                ConferenceDateTime.parse("2024-01-01T14:00"),
                ConferenceRoom.create(1, "TestRoom1", ConferenceRoomStatus.READY, "Location", 10).getValue()
        ).getValue();
        var conferenceToUpdate = Conference.create(2,
                ConferenceDateTime.parse("2024-01-01T08:00"),
                ConferenceDateTime.parse("2024-01-01T09:00"),
                ConferenceRoom.create(1, "TestRoom1", ConferenceRoomStatus.READY, "Location", 10).getValue()
        ).getValue();
        var backOffice = new BackOffice();
        backOffice.addConference(existingConference);
        backOffice.addConference(conferenceToUpdate);

        var conference = Conference.create(conferenceToUpdate.getId(),
                ConferenceDateTime.parse(start),
                ConferenceDateTime.parse(end),
                conferenceToUpdate.getRoom()
        ).getValue();
        var result = backOffice.updateConference(conference);

        Assertions.assertTrue(result.isFailure());
        Assertions.assertTrue(result.getErrorMessage().startsWith("There is already a conference between"));
    }
}
