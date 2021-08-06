package com.org.flights.service;


import com.org.flights.model.Flight;
import com.org.flights.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FlightService {

    private final  FlightRepository flightRepository;

    public List<Flight> findAllFlights() {
        List<Flight> debugFlight = flightRepository.findAll();

        return flightRepository.findAll();
    }

    public Optional<Flight> findFlightById(Long id) {
        return flightRepository.findById(id);
    }

    public Flight storeFlight(Flight flight) {
        return flightRepository.save(flight);
    }

}

