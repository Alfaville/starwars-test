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
@Table(name = "specie")
public class SpecieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "classification")
    private String classification;

    @Column(name = "designation")
    private String designation;

    @Column(name = "average_height")
    private String averageHeight;

    @Column(name = "average_lifespan")
    private String averageLifespan;

    @Column(name = "language")
    private String language;
}
