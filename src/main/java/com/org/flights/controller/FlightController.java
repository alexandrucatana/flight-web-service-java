package com.org.flights.controller;

import com.org.flights.model.Flight;
import com.org.flights.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/flights")
@RequiredArgsConstructor
@Log
public class FlightController {

    private final FlightService flightService;

    // Get All Flights
    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.findAllFlights());
    }


    // Get a Single Flight
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable(value = "id") Long flightId) {
        Optional<Flight> flight = flightService.findFlightById(flightId);

        if (!flight.isPresent()) {
            log.warning("Id " + flightId + " does not exist");
            Map<String, String> errorBody = new LinkedHashMap<>();
            errorBody.put("Error: ", "Id " + flightId + " not found!");

            return new ResponseEntity(errorBody, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(flight.get());
    }

    @PostMapping
    public ResponseEntity<Flight> saveFlight(@Valid @RequestBody Optional<Flight> flight, UriComponentsBuilder ucBuilder) {

        if (!flight.isPresent()) {
            log.warning("Flight has no content");
            Map<String, String> errorBody = new LinkedHashMap<>();

            return new ResponseEntity(errorBody, HttpStatus.NO_CONTENT);
        }
        
        flightService.storeFlight(flight.get());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(flight.get().getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}