package org.conference.web.backoffice.conferenceroom;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CONFERENCE_ROOM")
public class ConferenceRoomDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public int id;

    @Column(name = "NAME", nullable = false)
    public String name;

    @Column(name = "STATUS", nullable = false)
    public String status;

    @Column(name = "LOCATION", nullable = false)
    public String location;

    @Column(name = "MAX_CAPACITY", nullable = false)
    public int maxCapacity;

    public ConferenceRoomDao(String name, String status, String location, int maxCapacity) {
        this.name = name;
        this.status = status;
        this.location = location;
        this.maxCapacity = maxCapacity;
    }
}
