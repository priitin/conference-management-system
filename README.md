# Conference Management System

## Setup

### Prerequisites

If you want to build and run the application on your machine directly you need:

1. [Java 22 JDK](https://www.oracle.com/java/technologies/downloads/#java22)
2. [Apache Maven](https://maven.apache.org/download.cgi)

### Building, testing and running

Building the application:

    mvn compile

Running unit tests:

    mvn test

## Requirements

The Conference Management System will need to contain at least 2 modules: the **Back Office Gateway** and the **Conference Gateway**.

The **Back Office Gateway** should support the ability to:

- Create new conference room(s)
  - Users should have the option to create new rooms with multiple parameters:
    - room name
    - room status
    - room location (the physical address of the place where this room located)
    - room max capacity
- Create new conferences
  - Users should have the option to create new conferences in a selected conference room at a specified date and time. Before creating a conference, the system should also make multiple validations, for example: 
    - is a conference room available at the specified date and time
    - is a conference room available based on status (room status could be UNDER_CONSTRUCTION)
    - etc.
- Cancel conferences
  - Users should have the option to cancel a conference that has not yet happened. The system should also cancel all the registrations for this conference.
- Check conference availability
  - Users should have the option to check conference availability based on registered participants and conference room max capacity.
- Update a conference's date/time and room
  - Users should have the option to update a conference's date and time and also the room where conference will be done.
- Update a conference room's status and max capacity
  - Users should have the option to update a conference room's status and max capacity. The system should also apply validations such as:
    - check if a room's status has changed to UNDER_CONSTRUCTION and there are any conferences in the future
    - if a room's max capacity has decreased and if there are any conferences where the number of registered participants is more than the new max capacity
    - etc.
- Find feedback about a conference
  - Users should have the option to find feedback from participants. Feedback should have content and an author.
    - **NB!** Due to GDPR regulations, the system should only show the participant's first name and the first letter of the last name, for example Yury R************.

The **Conference Gateway** should support the ability to:

- Find available conferences
  - Users should have the option to find available conferences for a specified date and time range. The system should also provide information such as the amount of participates that are already registered for this conference and the conference location.
- Do self-registration
  - Users should have the option to register themselves to a selected conference by filling their information, like their full name, gender, email address, date of birth, etc.
  - The system should also validate this information, for example, does the conference room's max capacity allow this new participant, is the conference canceled, etc.
  - After a successful registration, the system should also provide a unique conference participant code
    - **NB!** An internal identifier cannot be used, the system should generate some other unique code
- Cancel registrations
  - Users should have the option to use their unique conference participant code to cancel their registration.
- Send conference feedback
  - Users should have the option to add feedback for a conference using their unique conference participant code.
