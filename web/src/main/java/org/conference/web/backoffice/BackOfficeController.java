package org.conference.web.backoffice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.conference.model.backoffice.BackOffice;
import org.conference.model.backoffice.Conference;
import org.conference.model.backoffice.ConferenceTimeRange;
import org.conference.web.backoffice.conference.ConferenceService;
import org.conference.web.backoffice.conference.CreateConferenceDto;
import org.conference.web.backoffice.conference.UpdateConferenceDto;
import org.conference.web.backoffice.conferenceroom.ConferenceRoomService;
import org.conference.web.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "back-office")
@RestController
@SuppressWarnings("rawtypes")
public class BackOfficeController {

    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private ConferenceRoomService conferenceRoomService;

    @Operation(hidden = true)
    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }

    @Operation(summary = "Gets all of the conferences")
    @ApiResponse(responseCode = "200", description = "Operation succeeded")
    @GetMapping("/conferences")
    public ResponseEntity getConferences() {
        var conferences = this.conferenceService.getRepository().getAll();
        return ResponseEntity.ok(conferences);
    }

    @Operation(summary = "Gets a conference by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation succeeded"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @GetMapping("/conferences/{id}")
    public ResponseEntity getConference(@PathVariable("id") int id) {
        var conference = this.conferenceService.getRepository().findById(id);

        if (conference.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Could not find a conference with id: " + id);

        return ResponseEntity.ok(conference.get());
    }

    @Operation(summary = "Creates a new conference",
            description =
                    "The start and end times have to be valid Java ISO 8601 timestamps. Conference times are " +
                    "accurate to the minute, any precision past that will be truncated."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation succeeded"),
            @ApiResponse(responseCode = "400", description = "Client error"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PostMapping("/conferences")
    public ResponseEntity createConference(@RequestBody CreateConferenceDto conferenceDto) {
        var room = this.conferenceRoomService.findById(conferenceDto.conferenceRoomId);
        if (room.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Could not find a conference room with id: " + conferenceDto.conferenceRoomId);

        var timeRangeResult = ConferenceTimeRange.create(conferenceDto.start, conferenceDto.end);
        if (timeRangeResult.isFailure())
            return ResultUtils.toResponseEntity(timeRangeResult);

        var conferenceResult = Conference.create(timeRangeResult.getValue(), room.get());
        if (conferenceResult.isFailure())
            return ResultUtils.toResponseEntity(conferenceResult);

        var conferences = this.conferenceService.getAll();
        var backOffice = new BackOffice(conferences);
        var conference = conferenceResult.getValue();

        var hasConflicts = backOffice.hasConflicts(conference);
        if (hasConflicts.isFailure())
            return ResultUtils.toResponseEntity(hasConflicts);

        backOffice.addConference(conference);

        var conferenceDao = this.conferenceService.save(conference);
        return ResponseEntity.ok(conferenceDao);
    }

    @Operation(summary = "Updates an existing conference",
            description =
                    "You can update the time and room. The start and end times have to be valid Java ISO 8601 " +
                    "timestamps. Conference times are accurate to the minute, any precision past that will be truncated."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation succeeded"),
            @ApiResponse(responseCode = "400", description = "Client error"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PutMapping("/conferences/{id}")
    public ResponseEntity updateConference(
            @PathVariable("id") int id,
            @RequestBody UpdateConferenceDto updateConferenceDto
    ) {
        var optionalConference = this.conferenceService.findById(id);
        if (optionalConference.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Could not find a conference with id: " + id);

        var timeRangeResult = ConferenceTimeRange.create(updateConferenceDto.newStart, updateConferenceDto.newEnd);
        if (timeRangeResult.isFailure())
            return ResultUtils.toResponseEntity(timeRangeResult);

        var conference = optionalConference.get();
        conference.changeTime(timeRangeResult.getValue());

        var optionalRoom = this.conferenceRoomService.findById(updateConferenceDto.newConferenceRoomId);
        if (optionalRoom.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Could not find a conference room with id: " + updateConferenceDto.newConferenceRoomId);

        var changeRoomResult = conference.changeRoom(optionalRoom.get());
        if (changeRoomResult.isFailure())
            return ResultUtils.toResponseEntity(changeRoomResult);

        var conferences = this.conferenceService.getAll();
        var backOffice = new BackOffice(conferences);

        var hasConflicts = backOffice.hasConflicts(conference);
        if (hasConflicts.isFailure())
            return ResultUtils.toResponseEntity(hasConflicts);

        backOffice.updateConference(conference);
        var conferenceDao = this.conferenceService.save(conference);
        return ResponseEntity.ok(conferenceDao);
    }

    @Operation(summary = "Gets all of the conference rooms")
    @ApiResponse(responseCode = "200", description = "Operation succeeded")
    @GetMapping("/conference-rooms")
    public ResponseEntity getConferenceRooms() {
        var rooms = this.conferenceRoomService.getRepository().getAll();
        return ResponseEntity.ok(rooms);
    }

    @Operation(summary = "Gets a conference room by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation succeeded"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @GetMapping("/conference-rooms/{id}")
    public ResponseEntity getConferenceRoom(@PathVariable("id") int id) {
        var room = this.conferenceRoomService.getRepository().findById(id);

        if (room.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Could not find a conference room with id: " + id);

        return ResponseEntity.ok(room.get());
    }
}
