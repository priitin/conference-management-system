package org.conference.web.backoffice;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class BackOfficeController {

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepo;
    @Autowired
    private ConferenceRepository conferenceRepo;

    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/conferences");
    }

    @GetMapping("/conferences")
    public List<ConferenceDao> getConferences() {
        return this.conferenceRepo.getAll();
    }

    @GetMapping("/conferences/{id}")
    public ConferenceDao getConference(@PathVariable("id") int id) {
        var conference = this.conferenceRepo.findById(id);
        return conference.orElseGet(ConferenceDao::new);
    }

    @GetMapping("/conference-rooms")
    public List<ConferenceRoomDao> getConferenceRooms() {
        return this.conferenceRoomRepo.getAll();
    }

    @GetMapping("/conference-rooms/{id}")
    public ConferenceRoomDao getConferenceRoom(@PathVariable("id") int id) {
        var room = this.conferenceRoomRepo.findById(id);
        return room.orElseGet(ConferenceRoomDao::new);
    }
}
