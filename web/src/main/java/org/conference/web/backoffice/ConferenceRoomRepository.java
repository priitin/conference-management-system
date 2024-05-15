package org.conference.web.backoffice;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRoomRepository extends CrudRepository<ConferenceRoomDao, Integer> {

    @Query("SELECT room FROM CONFERENCE_ROOM room")
    List<ConferenceRoomDao> getAll();
}
