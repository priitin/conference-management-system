package org.conference.web.backoffice.conferenceroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoomDao, Integer> {

    @Query("SELECT room FROM CONFERENCE_ROOM room")
    List<ConferenceRoomDao> getAll();
}
