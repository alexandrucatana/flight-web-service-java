package com.org.flights;

import com.org.flights.model.Flight;
import com.org.flights.repository.FlightRepository;
import com.org.flights.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class TestFlightService {

    @MockBean
    private FlightRepository flightRepository;

    private FlightService flightService;

    @BeforeEach
    public void init() {
        flightService = new FlightService(flightRepository);
    }

    @Test
    public void whenValidId_thenFlightShouldBeFound() {

        Flight blueAir = buildFlights().get(0);
        blueAir.setId(new Long(1));
        Mockito.when(flightRepository.findById(blueAir.getId())).thenReturn(Optional.of(blueAir));

        Long givenId = new Long(1);
        Optional<Flight> foundFlight = flightService.findFlightById(givenId);
        assertEquals(foundFlight.get().getId(), givenId);
    }

    private List<Flight> buildFlights() {
        Flight flight_one = new Flight("BlueAir", "Madrid", "Tokyo");
        Flight flight_two = new Flight("Lufthansa", "Bucharest", "Stuttgart");

        List<Flight> flightList = Arrays.asList(flight_one, flight_two);

        return flightList;
    }
}
