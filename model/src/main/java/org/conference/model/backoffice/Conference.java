package org.conference.model.backoffice;

import org.conference.model.common.ConferenceDateTime;
import org.conference.model.common.Result;
import org.conference.model.common.ResultOf;

import java.util.ArrayList;

public class Conference {
    private int id;
    private ConferenceDateTime start;
    private ConferenceDateTime end;
    private ConferenceRoom room;
    private ArrayList<Participant> participants;

    private Conference(int id, ConferenceDateTime start, ConferenceDateTime end, ConferenceRoom room,
                       ArrayList<Participant> participants) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.room = room;
        this.participants = participants;
    }

    /**
     * Create a Conference without any participants.
     */
    public static ResultOf<Conference> create(int id, ConferenceDateTime start, ConferenceDateTime end, ConferenceRoom room) {
        if (start.isEqual(end) || start.isAfter(end))
            return Result.failure("Conference start time has to be before its end time");
        if (room.getStatus() == ConferenceRoomStatus.UNDER_CONSTRUCTION)
            return Result.failure("Conference room '%s' is under construction".formatted(room.getName()));

        return Result.succeed(new Conference(id, start, end, room, new ArrayList<>()));
    }
}
