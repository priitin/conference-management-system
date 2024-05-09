package org.conference.model.backoffice;

import lombok.Getter;
import org.conference.model.common.Entity;
import org.conference.model.common.Result;
import org.conference.model.common.ResultOf;

import java.util.ArrayList;

public class Conference extends Entity {
    @Getter
    private ConferenceTimeRange timeRange;
    @Getter
    private ConferenceRoom room;
    private ArrayList<Participant> participants;

    private Conference(int id, ConferenceTimeRange timeRange, ConferenceRoom room,
                       ArrayList<Participant> participants) {
        this.id = id;
        this.timeRange = timeRange;
        this.room = room;
        this.participants = participants;
    }

    public ConferenceDateTime getStart() {
        return this.timeRange.getStart();
    }

    public ConferenceDateTime getEnd() {
        return this.timeRange.getEnd();
    }

    /**
     * Create a Conference without any participants.
     */
    public static ResultOf<Conference> create(int id, ConferenceTimeRange timeRange, ConferenceRoom room) {
        if (room.getStatus() == ConferenceRoomStatus.UNDER_CONSTRUCTION)
            return Result.failure("Conference room '%s' is under construction".formatted(room.getName()));

        return Result.succeed(new Conference(id, timeRange, room, new ArrayList<>()));
    }

    public void changeTime(ConferenceTimeRange timeRange) {
        this.timeRange = timeRange;
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
        return new Conference(this.id, this.timeRange, this.room, this.participants);
    }

    @Override
    public String toString() {
        return "%s: %s".formatted(this.room.getName(), this.timeRange);
    }
}
