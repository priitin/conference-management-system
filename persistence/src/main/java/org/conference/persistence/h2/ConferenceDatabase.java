package org.conference.persistence.h2;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConferenceDatabase {
    private final Connection connection;
    private final Statement statement;

    /**
     * Creates a connection to the H2 in-memory database.
     */
    @SneakyThrows
    public ConferenceDatabase() {
        this.connection = DriverManager.getConnection("jdbc:h2:mem:conference");
        this.statement = this.connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public void create() {
        this.createTables();
        this.seedTables();
    }

    @SneakyThrows
    private void createTables() {
        var sql = "";

        sql = """
                CREATE TABLE ConferenceRoom (
                    Id INT PRIMARY KEY AUTO_INCREMENT,
                    Name NVARCHAR(255) NOT NULL,
                    Status NVARCHAR(255) NOT NULL,
                    Location NVARCHAR(255) NOT NULL,
                    MaxCapacity SMALLINT NOT NULL
                );""";
        this.statement.execute(sql);

        sql = """
                CREATE TABLE Conference (
                    Id INT PRIMARY KEY AUTO_INCREMENT,
                    StartTime TIMESTAMP(0) WITH TIME ZONE NOT NULL,
                    EndTime TIMESTAMP(0) WITH TIME ZONE NOT NULL,
                    ConferenceRoomId INT NOT NULL,

                    CONSTRAINT FK_Conference_ConferenceRoom
                        FOREIGN KEY (ConferenceRoomId) REFERENCES ConferenceRoom(Id)
                );""";
        this.statement.execute(sql);
    }

    @SneakyThrows
    private void seedTables() {
        var sql = "";

        sql = """
                INSERT INTO ConferenceRoom(Id, Name, Status, Location, MaxCapacity) VALUES
                	(1, 'Test Room 1', 'Ready', 'Here', 10),
                	(2, 'Test Room 2', 'Under construction', 'There', 4);
                """;
        statement.execute(sql);

        sql = """
                INSERT INTO Conference(StartTime, EndTime, ConferenceRoomId) VALUES
                	('2024-01-01T10:00Z', '2024-01-01T11:00Z', 1),
                	('2024-01-01T13:00Z', '2024-01-01T16:00Z', 2);
                """;
        statement.execute(sql);
    }

    @SneakyThrows
    public int getRowCount(String tableName) {
        var sql = "SELECT COUNT(*) FROM %s;".formatted(tableName);
        var result = this.statement.executeQuery(sql);

        if (result.first()) {
            return result.getInt(1);
        } else {
            return 0;
        }
    }

    @SneakyThrows
    public void closeConnection() {
        this.connection.close();
    }
}
