package org.conference.web.backoffice;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SuppressWarnings("rawtypes")
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
    public ResponseEntity getConferences() {
        var conferences = this.conferenceRepo.getAll();
        return ResponseEntity.ok(conferences);
    }

    @GetMapping("/conferences/{id}")
    public ResponseEntity getConference(@PathVariable("id") int id) {
        var conference = this.conferenceRepo.findById(id);

        if (conference.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Could not find a conference with id: " + id);

        return ResponseEntity.ok(conference.get());
    }

    @GetMapping("/conference-rooms")
    public ResponseEntity getConferenceRooms() {
        var rooms = this.conferenceRoomRepo.getAll();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/conference-rooms/{id}")
    public ResponseEntity getConferenceRoom(@PathVariable("id") int id) {
        var room = this.conferenceRoomRepo.findById(id);

        if (room.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Could not find a conference room with id: " + id);

        return ResponseEntity.ok(room.get());
    }
}
