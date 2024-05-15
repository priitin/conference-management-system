package org.conference.web;

import org.conference.web.backoffice.ConferenceDao;
import org.conference.web.backoffice.ConferenceRepository;
import org.conference.web.backoffice.ConferenceRoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class ApplicationStartup implements ApplicationRunner {

    @Autowired
    private ConferenceRepository conferenceRepo;

    @Override
    public void run(ApplicationArguments args) {
        this.seedDatabase();
    }

    private void seedDatabase() {
        var room1 = new ConferenceRoomDao("Test Room 1", "Ready", "Here", 10);
        var conference1 = new ConferenceDao(OffsetDateTime.parse("2024-01-01T10:00Z"), OffsetDateTime.parse("2024-01-01T11:00Z"), room1);

        var room2 = new ConferenceRoomDao("Test Room 2", "Under construction", "There", 4);
        var conference2 = new ConferenceDao(OffsetDateTime.parse("2024-01-01T13:00Z"), OffsetDateTime.parse("2024-01-01T16:00Z"), room2);

        var conferences = List.of(conference1, conference2);
        this.conferenceRepo.saveAll(conferences);
    }
}
