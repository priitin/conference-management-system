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

        this.replaceConference(conference);
    }

    /**
     * Checks if {@code conference} has conflicts with any existing conferences.
     */
    public Result hasConflicts(Conference conference) {
        var existingConferences = this.conferences.stream().filter(x -> !x.equals(conference));
        var withSameRoom = existingConferences.filter(x -> x.getRoom().equals(conference.getRoom()));
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
    private void replaceConference(Conference conference) {
        var indexToReplace = this.conferences.indexOf(conference);
        Contract.requires(indexToReplace >= 0, "Could not find conference with id " + conference.getId());

        this.conferences.set(indexToReplace, conference);
    }
}
