package org.conference.web.backoffice.conference;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CreateConferenceDto {
    public String start;
    public String end;
    public int conferenceRoomId;
}
