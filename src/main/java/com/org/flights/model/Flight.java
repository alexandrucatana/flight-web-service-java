package com.org.flights.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Flight implements Serializable {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Getter
    @Setter
    private String airlineName;

    @NotBlank
    @Getter
    @Setter
    private String start;

    @NotBlank
    @Getter
    @Setter
    private String destination;

    public Flight() {}

    public Flight(String airlineName, String start, String destination) {
        this.airlineName = airlineName;
        this.start = start;
        this.destination = destination;
    }
}