package org.conference.web.backoffice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<ConferenceDao, Integer> {

    @Query("SELECT c FROM CONFERENCE c")
    List<ConferenceDao> getAll();
}
