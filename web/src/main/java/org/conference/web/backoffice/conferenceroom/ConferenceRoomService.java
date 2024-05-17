package org.conference.web.backoffice.conferenceroom;

import lombok.SneakyThrows;
import org.conference.model.backoffice.ConferenceRoom;
import org.conference.model.backoffice.ConferenceRoomStatus;
import org.conference.model.common.ContractException;
import org.conference.web.util.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConferenceRoomService {

    @Autowired
    private ConferenceRoomRepository repo;

    public ConferenceRoomRepository getRepository() {
        return this.repo;
    }

    public Optional<ConferenceRoom> findById(int id) {
        var dao = this.repo.findById(id);
        if (dao.isEmpty())
            return Optional.empty();

        var room = ConferenceRoomService.mapToConferenceRoom(dao.get());
        return Optional.of(room);
    }

    public ConferenceRoom save(ConferenceRoom conferenceRoom) {
        var dao = ConferenceRoomService.mapToConferenceRoomDao(conferenceRoom);
        var result = this.repo.save(dao);
        return ConferenceRoomService.mapToConferenceRoom(result);
    }

    public List<ConferenceRoom> saveAll(List<ConferenceRoom> conferenceRooms) {
        var daos = conferenceRooms.stream().map(ConferenceRoomService::mapToConferenceRoomDao).toList();
        var results = this.repo.saveAll(daos);
        var conferenceRoomResults = IteratorUtils.toList(results).stream()
                .map(ConferenceRoomService::mapToConferenceRoom)
                .toList();
        return conferenceRoomResults;
    }

    public static ConferenceRoom mapToConferenceRoom(ConferenceRoomDao dao) {
        var status = parseConferenceRoomStatus(dao.status);
        var room = ConferenceRoom.create(dao.id, dao.name, status, dao.location, dao.maxCapacity);
        return room.getValue();
    }

    public static ConferenceRoomDao mapToConferenceRoomDao(ConferenceRoom conferenceRoom) {
        var cr = conferenceRoom;
        var status = toConferenceRoomStatusString(cr.getStatus());
        return new ConferenceRoomDao(cr.getId(), cr.getName(), status, cr.getLocation(), cr.getMaxCapacity());
    }

    @SneakyThrows
    private static ConferenceRoomStatus parseConferenceRoomStatus(String status) {
        return switch (status) {
            case "Ready" -> ConferenceRoomStatus.READY;
            case "Under construction" -> ConferenceRoomStatus.UNDER_CONSTRUCTION;
            default -> throw new ContractException(
                    "Could not convert '%s' to ConferenceRoomStatus".formatted(status));
        };
    }

    @SneakyThrows
    private static String toConferenceRoomStatusString(ConferenceRoomStatus status) {
        return switch (status) {
            case READY -> "Ready";
            case UNDER_CONSTRUCTION -> "Under construction";
            default -> throw new ContractException(
                    "Could not convert ConferenceRoomStatus '%s' to String".formatted(status.toString()));
        };
    }
}
