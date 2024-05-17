package org.conference.web.backoffice;

import org.conference.model.backoffice.Conference;
import org.conference.model.backoffice.ConferenceTimeRange;
import org.conference.web.util.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceService {

    @Autowired
    private ConferenceRepository repo;

    public ConferenceRepository getRepository() {
        return this.repo;
    }

    public List<Conference> getAll() {
        var daos = this.repo.getAll();
        return daos.stream().map(ConferenceService::mapToConference).toList();
    }

    public ConferenceDao save(Conference conference) {
        var dao = ConferenceService.mapToConferenceDao(conference);
        var result = this.repo.save(dao);
        return result;
    }

    public List<ConferenceDao> saveAll(List<Conference> conferences) {
        var daos = conferences.stream().map(ConferenceService::mapToConferenceDao).toList();
        var results = this.repo.saveAll(daos);
        return IteratorUtils.toList(results);
    }

    public static Conference mapToConference(ConferenceDao dao) {
        var room = ConferenceRoomService.mapToConferenceRoom(dao.conferenceRoom);
        var timeRange = ConferenceTimeRange.create(dao.startTime, dao.endTime);

        var conference = Conference.create(dao.id, timeRange.getValue(), room);
        return conference.getValue();
    }

    public static ConferenceDao mapToConferenceDao(Conference conference) {
        return new ConferenceDao(
                conference.getId(),
                conference.getStart().getValue(),
                conference.getEnd().getValue(),
                ConferenceRoomService.mapToConferenceRoomDao(conference.getRoom())
        );
    }
}
