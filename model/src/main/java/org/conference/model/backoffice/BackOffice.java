package org.conference.model.backoffice;

import lombok.Getter;
import org.conference.model.common.Result;

import java.util.ArrayList;
import java.util.Collection;

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

    public Result AddConference(Conference conference) {
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
}
