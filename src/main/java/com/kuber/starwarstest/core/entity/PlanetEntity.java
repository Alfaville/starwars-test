package com.kuber.starwarstest.core.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "planet")
public class PlanetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "climate")
    private String climate;

    @Column(name = "gravity")
    private String gravity;

    @Column(name = "terrain")
    private String terrain;

    @Column(name = "population")
    private String population;
}
