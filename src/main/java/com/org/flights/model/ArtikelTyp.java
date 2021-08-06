package com.org.flights.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artikelTyp")
public class ArtikelTyp {

    @Id
    @Getter
    private int artikelTypId;

    @Getter
    private String artikelTypFr;

    @Getter
    @Setter
    private String artikelTypDe;

    public ArtikelTyp(){ }

    public ArtikelTyp( Integer artikelTypId, String artikelTypFr, String artikelTypeDe){
        this.artikelTypId = artikelTypId;
        this.artikelTypFr = artikelTypFr;
        this.artikelTypDe = artikelTypeDe;
    }

}