package org.conference.persistence.h2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConferenceDatabaseTests {

    @Test
    public void creating_the_database_should_create_tables_and_seed_data() {
        var db = new ConferenceDatabase();

        db.create();

        Assertions.assertEquals(2, db.getRowCount("Conference"));
        Assertions.assertEquals(2, db.getRowCount("ConferenceRoom"));
        db.closeConnection();
    }
}
