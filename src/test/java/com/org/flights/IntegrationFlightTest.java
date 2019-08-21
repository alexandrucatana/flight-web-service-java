package com.org.flights;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.flights.model.Flight;
import com.org.flights.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationFlightTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightRepository flightRepository;

    private List<Flight> mainFlightList;

    @BeforeEach
    public void init() {
        Flight flight1 = new Flight("Turkish Airlines", "Madrid", "Frankfurt");
        flight1.setId(1L);
        Flight flight2 = new Flight("British Airways", "London", "New York");
        flight2.setId(2L);

        flightRepository.save(flight1);
        flightRepository.save(flight2);

        mainFlightList = Arrays.asList(flight1, flight2);
    }


    @Test
    public void find_flighId_OK() throws Exception {
        mockMvc.perform(get("/v1/flights/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.airlineName", is("Turkish Airlines")))
                .andExpect(jsonPath("$.start", is("Madrid")))
                .andExpect(jsonPath("$.destination", is("Frankfurt")))
        ;

        mockMvc.perform(get("/v1/flights/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.airlineName", is("British Airways")))
                .andExpect(jsonPath("$.start", is("London")))
                .andExpect(jsonPath("$.destination", is("New York")))
        ;
    }

    @Test
    public void find_flighId_NotFound() throws Exception {
        mockMvc.perform(get("/v1/flights/3")).andExpect(status().isNotFound());
    }

    @Test
    public void whenFlightList_thenGetFlightPresent() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/flights")).andReturn();
        String jsonString = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<Flight> flightsFromUrl = mapper.readValue(jsonString, new TypeReference<ArrayList<Flight>>() {});

        assertEquals(flightsFromUrl.get(0).getId(), mainFlightList.get(0).getId());
        assertEquals(flightsFromUrl.get(1).getId(), mainFlightList.get(1).getId());

        assertEquals(flightsFromUrl.get(0).getAirlineName(), mainFlightList.get(0).getAirlineName());
        assertEquals(flightsFromUrl.get(1).getAirlineName(), mainFlightList.get(1).getAirlineName());

        assertEquals(flightsFromUrl.get(0).getStart(), mainFlightList.get(0).getStart());
        assertEquals(flightsFromUrl.get(1).getStart(), mainFlightList.get(1).getStart());

        assertEquals(flightsFromUrl.get(0).getDestination(), mainFlightList.get(0).getDestination());
        assertEquals(flightsFromUrl.get(1).getDestination(), mainFlightList.get(1).getDestination());
    }
}
