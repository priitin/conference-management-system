package org.conference.model.backoffice;

import lombok.Getter;
import org.conference.model.common.Entity;
import org.conference.model.common.Result;
import org.conference.model.common.ResultOf;
import org.conference.model.common.StringUtils;

@Getter
public class ConferenceRoom extends Entity {
    private String name;
    private ConferenceRoomStatus status;
    private String location;
    private int maxCapacity;

    private ConferenceRoom(int id, String name, ConferenceRoomStatus status, String location, int maxCapacity) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.location = location;
        this.maxCapacity = maxCapacity;
    }

    public static ResultOf<ConferenceRoom> create(int id, String name, ConferenceRoomStatus status, String location,
                                                  int maxCapacity) {
        if (StringUtils.isNullOrBlank(name))
            return Result.ofFail("Conference room name cannot be empty");
        if (StringUtils.isNullOrBlank(location))
            return Result.ofFail("Conference room location cannot be empty");
        if (maxCapacity <= 0)
            return Result.ofFail("Conference room's maximum capacity has to be a positive integer");

        return Result.succeed(new ConferenceRoom(id, name, status, location, maxCapacity));
    }

    public static ResultOf<ConferenceRoom> create(String name, ConferenceRoomStatus status, String location,
                                                  int maxCapacity) {
        return ConferenceRoom.create(0, name, status, location, maxCapacity);
    }
}
