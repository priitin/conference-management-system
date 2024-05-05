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

    public Result addConference(Conference conference) {
        for (var existingConference : this.conferences) {
            if (conference.getStart().isBeforeOrEqual(existingConference.getEnd())
                    && conference.getEnd().isAfterOrEqual(existingConference.getStart())) {
                return Result.fail("There is already a conference between %s - %s".formatted(
                        existingConference.getStart().toString(), existingConference.getEnd().toString()));
            }
        }

        this.conferences.add(conference);
        return Result.succeed();
    }

    public Result updateConference(Conference conference) {
        var conferenceToUpdate = this.getConference(conference.getId());

        var existingConferences = this.conferences.stream()
                .filter(x -> x.getId() != conferenceToUpdate.getId())
                .toList();
        for (var existingConference : existingConferences) {
            if (conference.getStart().isBeforeOrEqual(existingConference.getEnd())
                    && conference.getEnd().isAfterOrEqual(existingConference.getStart())) {
                return Result.fail("There is already a conference between %s - %s".formatted(
                        existingConference.getStart().toString(), existingConference.getEnd().toString()));
            }
        }

        this.replaceConference(conferenceToUpdate, conference);
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
