package org.conference.model.backoffice;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.conference.model.common.Entity;
import org.conference.model.common.Result;
import org.conference.model.common.ResultOf;

public class ConferenceRoom extends Entity {
    @Getter
    private String name;
    @Getter
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
        if (StringUtils.isBlank(name))
            return Result.failure("Conference room name cannot be empty");
        if (StringUtils.isBlank(location))
            return Result.failure("Conference room location cannot be empty");
        if (maxCapacity <= 0)
            return Result.failure("Conference room's maximum capacity has to be a positive integer");

        return Result.succeed(new ConferenceRoom(id, name, status, location, maxCapacity));
    }
}
