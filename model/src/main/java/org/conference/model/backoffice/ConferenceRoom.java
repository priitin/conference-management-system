package org.conference.model.backoffice;

import org.apache.commons.lang3.StringUtils;

public class ConferenceRoom {
    private int id;
    private String name;
    private ConferenceRoomStatus status;
    private String location;
    private int maxCapacity;

    public ConferenceRoom(int id, String name, ConferenceRoomStatus status, String location, int maxCapacity)
            throws Exception {
        if (StringUtils.isBlank(name))
            throw new Exception("Conference room name cannot be empty");
        if (StringUtils.isBlank(location))
            throw new Exception("Conference room location cannot be empty");
        if (maxCapacity <= 0)
            throw new Exception("Conference room's maximum capacity has to be a positive integer");

        this.id = id;
        this.name = name;
        this.status = status;
        this.location = location;
        this.maxCapacity = maxCapacity;
    }
}
