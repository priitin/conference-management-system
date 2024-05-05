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
    private ArrayList<Conference> conferences;

    public BackOffice() {
        this.conferences = new ArrayList<>();
    }

    public BackOffice(Conference conference) {
        this.conferences = new ArrayList<>() {{
            add(conference);
        }};
    }

    public BackOffice(Collection<Conference> conferences) {
        this.conferences = new ArrayList<>(conferences);
    }

    @SneakyThrows
    public void addConference(Conference conference) {
        Result conflicts = this.hasConflicts(conference);
        Contract.requires(conflicts.isSuccess(), conflicts.getErrorMessage());

        this.conferences.add(conference);
    }

    @SneakyThrows
    public void updateConference(Conference conference) {
        Result conflicts = this.hasConflicts(conference);
        Contract.requires(conflicts.isSuccess(), conflicts.getErrorMessage());

        var conferenceToUpdate = this.getConference(conference.getId());
        this.replaceConference(conferenceToUpdate, conference);
    }

    /**
     * Checks if {@code conference} has conflicts with any existing conferences.
     */
    public Result hasConflicts(Conference conference) {
        var existingConferences = this.conferences.stream()
                .filter(x -> x.getId() != conference.getId())
                .toList();

        for (var existingConference : existingConferences) {
            if (conference.getRoom().getId() == existingConference.getRoom().getId()
                    && conference.getStart().isBeforeOrEqual(existingConference.getEnd())
                    && conference.getEnd().isAfterOrEqual(existingConference.getStart())) {
                return Result.fail("There is already a conference in the room %s between %s - %s".formatted(
                        existingConference.getRoom().getName(),
                        existingConference.getStart().toString(), existingConference.getEnd().toString()));
            }
        }

        return Result.succeed();
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
