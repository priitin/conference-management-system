-- Create tables
CREATE TABLE ConferenceRoom (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name NVARCHAR(255) NOT NULL,
    Status NVARCHAR(255) NOT NULL,
    Location NVARCHAR(255) NOT NULL,
    MaxCapacity SMALLINT NOT NULL
);

CREATE TABLE Conference (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    StartTime TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    EndTime TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    ConferenceRoomId INT NOT NULL,

    CONSTRAINT FK_Conference_ConferenceRoom
        FOREIGN KEY (ConferenceRoomId) REFERENCES ConferenceRoom(Id)
);

-- Seed data
INSERT INTO ConferenceRoom(Id, Name, Status, Location, MaxCapacity) VALUES
    (1, 'Test Room 1', 'Ready', 'Here', 10),
    (2, 'Test Room 2', 'Under construction', 'There', 4);

INSERT INTO Conference(StartTime, EndTime, ConferenceRoomId) VALUES
    ('2024-01-01T10:00Z', '2024-01-01T11:00Z', 1),
    ('2024-01-01T13:00Z', '2024-01-01T16:00Z', 2);
