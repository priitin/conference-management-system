package org.conference.web;

import org.conference.model.backoffice.Conference;
import org.conference.model.backoffice.ConferenceRoom;
import org.conference.model.backoffice.ConferenceRoomStatus;
import org.conference.model.backoffice.ConferenceTimeRange;
import org.conference.web.backoffice.conferenceroom.ConferenceRoomService;
import org.conference.web.backoffice.conference.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartup implements ApplicationRunner {

    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private ConferenceRoomService conferenceRoomService;

    @Override
    public void run(ApplicationArguments args) {
        this.seedDatabase();
    }

    private void seedDatabase() {
        var room1 = ConferenceRoom.create("Test Room 1", ConferenceRoomStatus.READY, "Here", 10).getValue();
        room1 = this.conferenceRoomService.save(room1);
        var room2 = ConferenceRoom.create("Test Room 2", ConferenceRoomStatus.UNDER_CONSTRUCTION, "There", 4).getValue();
        this.conferenceRoomService.save(room2);

        var conference1 = Conference.create(ConferenceTimeRange.parse("2024-01-01T10:00", "2024-01-01T11:00"), room1).getValue();
        var conference2 = Conference.create(ConferenceTimeRange.parse("2024-01-01T13:00", "2024-01-01T16:00"), room1).getValue();

        var conferences = List.of(conference1, conference2);
        this.conferenceService.saveAll(conferences);
    }
}
