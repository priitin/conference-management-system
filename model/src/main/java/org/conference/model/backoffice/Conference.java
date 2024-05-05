package org.conference.model.backoffice;

import lombok.Getter;
import org.conference.model.common.ConferenceDateTime;
import org.conference.model.common.Result;
import org.conference.model.common.ResultOf;

import java.util.ArrayList;

public class Conference {
    @Getter
    private final int id;
    @Getter
    private ConferenceDateTime start;
    @Getter
    private ConferenceDateTime end;
    @Getter
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
        if (start.isAfterOrEqual(end))
            return Result.failure("Conference start time has to be before its end time");
        if (room.getStatus() == ConferenceRoomStatus.UNDER_CONSTRUCTION)
            return Result.failure("Conference room '%s' is under construction".formatted(room.getName()));

        return Result.succeed(new Conference(id, start, end, room, new ArrayList<>()));
    }

    public Result changeTime(ConferenceDateTime start, ConferenceDateTime end) {
        if (start.isAfterOrEqual(end))
            return Result.failure("Conference start time has to be before its end time");

        this.start = start;
        this.end = end;
        return Result.succeed();
    }

    public Result changeRoom(ConferenceRoom room) {
        if (room.getStatus() == ConferenceRoomStatus.UNDER_CONSTRUCTION)
            return Result.failure("Conference room '%s' is under construction".formatted(room.getName()));

        this.room = room;
        return Result.succeed();
    }

    /**
     * @return a new {@code Conference} with all its values copied.
     */
    public Conference copy() {
        return new Conference(this.id, this.start, this.end, this.room, this.participants);
    }

    @Override
    public String toString() {
        return "%s: %s - %s".formatted(this.room.getName(), this.start, this.end);
    }
}
