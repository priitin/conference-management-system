package org.conference.model.backoffice;

import org.apache.commons.lang3.StringUtils;
import org.conference.model.common.Contract;
import org.conference.model.common.ContractException;

public class ConferenceRoom {
    private int id;
    private String name;
    private ConferenceRoomStatus status;
    private String location;
    private int maxCapacity;

    public ConferenceRoom(int id, String name, ConferenceRoomStatus status, String location, int maxCapacity)
            throws ContractException
    {
        Contract.Requires(!StringUtils.isBlank(name), "Conference room name cannot be empty");
        Contract.Requires(!StringUtils.isBlank(location), "Conference room location cannot be empty");
        Contract.Requires(maxCapacity > 0, "Conference room's maximum capacity has to be a positive integer");

        this.id = id;
        this.name = name;
        this.status = status;
        this.location = location;
        this.maxCapacity = maxCapacity;
    }
}
