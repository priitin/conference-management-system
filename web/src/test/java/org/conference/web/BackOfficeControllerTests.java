package org.conference.web;

import org.conference.web.backoffice.BackOfficeController;
import org.conference.web.backoffice.ConferenceRepository;
import org.conference.web.backoffice.ConferenceRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BackOfficeController.class)
public class BackOfficeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConferenceRepository conferenceRepo;
    @MockBean
    private ConferenceRoomRepository conferenceRoomRepo;

    @Test
    public void getting_a_conference_that_doesnt_exist_should_return_error_404() throws Exception {
        when(conferenceRepo.findById(0)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/conferences/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Could not find a conference with id")));
    }

    @Test
    public void getting_a_conference_room_that_doesnt_exist_should_return_error_404() throws Exception {
        when(conferenceRoomRepo.findById(0)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/conference-rooms/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Could not find a conference room with id")));
    }
}