package org.conference.model.backoffice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ConferenceRoomTests {

    @Test
    public void creating_a_valid_conference_room_should_succeed() {
        var roomResult = ConferenceRoom.create(1, "Room", ConferenceRoomStatus.READY, "Here", 3);

        Assertions.assertTrue(roomResult.isSuccess());
    }

    @ParameterizedTest
    @CsvSource({
            ",READY,Location,1,Conference room name cannot be empty",
            "RoomName,READY,,1,Conference room location cannot be empty",
            "RoomName,READY,Location,0,Conference room's maximum capacity has to be a positive integer",
    })
    public void creating_an_invalid_conference_room_should_fail(String name, ConferenceRoomStatus status,
            String location, int maxCapacity, String expectedErrorMessage) {
        var roomResult = ConferenceRoom.create(1, name, status, location, maxCapacity);

        Assertions.assertTrue(roomResult.isFailure());
        Assertions.assertEquals(expectedErrorMessage, roomResult.getErrorMessage());
    }
}
