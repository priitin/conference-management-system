package org.conference.model.backoffice;

import lombok.Getter;
import lombok.SneakyThrows;
import org.conference.model.common.Contract;
import org.conference.model.common.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class BackOffice {
    @Getter
    private final ArrayList<Conference> conferences;

    public BackOffice() {
        this.conferences = new ArrayList<>();
    }

    public BackOffice(Conference conference) {
        this.conferences = new ArrayList<>() {{
            add(conference);
        }};
    }

    public BackOffice(Conference... conferences) {
        this.conferences = new ArrayList<>();

        for (var conf : conferences) {
            this.addConference(conf);
        }
    }

    public BackOffice(Collection<Conference> conferences) {
        this.conferences = new ArrayList<>();

        for (var conf : conferences) {
            this.addConference(conf);
        }
    }

    @SneakyThrows
    public void addConference(Conference conference) {
        Contract.requiresSuccess(this.hasConflicts(conference));

        this.conferences.add(conference);
    }

    @SneakyThrows
    public void updateConference(Conference conference) {
        Contract.requiresSuccess(this.hasConflicts(conference));

        var conferenceToUpdate = this.getConference(conference.getId());
        this.replaceConference(conferenceToUpdate, conference);
    }

    /**
     * Checks if {@code conference} has conflicts with any existing conferences.
     */
    public Result hasConflicts(Conference conference) {
        var existingConferences = this.conferences.stream().filter(x -> x.getId() != conference.getId());
        var withSameRoom = existingConferences.filter(x -> x.getRoom().getId() == conference.getRoom().getId());
        var withIntersectingTimeRange = withSameRoom.filter(x -> x.getTimeRange().intersects(conference.getTimeRange()));

        var optionalConflictingConference = withIntersectingTimeRange.findFirst();
        if (optionalConflictingConference.isPresent()) {
            var conflicting = optionalConflictingConference.get();
            return Result.fail("There is already a conference in the room %s between %s".formatted(
                    conflicting.getRoom().getName(), conflicting.getTimeRange()));
        } else {
            return Result.succeed();
        }
    }

    @SneakyThrows
    private Conference getConference(int id) {
        var conference = this.findConference(id);
        Contract.requires(conference.isPresent(), "Could not find conference with id " + id);

        return conference.get();
    }

    private Optional<Conference> findConference(int id) {
        return this.conferences.stream().filter(x -> x.getId() == id).findFirst();
    }

    private void replaceConference(Conference before, Conference after) {
        var indexToReplace = this.conferences.indexOf(before);
        this.conferences.set(indexToReplace, after);
    }
}
