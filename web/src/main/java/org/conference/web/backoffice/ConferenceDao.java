package org.conference.web.backoffice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CONFERENCE")
public class ConferenceDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public int id;

    @Column(name = "START_TIME", nullable = false)
    public OffsetDateTime startTime;

    @Column(name = "END_TIME", nullable = false)
    public OffsetDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "CONFERENCE_ROOM_ID")
    public ConferenceRoomDao conferenceRoom;

    public ConferenceDao(OffsetDateTime startTime, OffsetDateTime endTime, ConferenceRoomDao conferenceRoom) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.conferenceRoom = conferenceRoom;
    }
}
