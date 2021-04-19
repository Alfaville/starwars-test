package com.kuber.starwarstest.core.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "person")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mass")
    private Integer mass;

    @Column(name = "height")
    private Integer height;

    @Column(name = "hair_color")
    private String hairColor;

    @Column(name = "eye_color")
    private String eyeColor;

    @Column(name = "skin_color")
    private String skinColor;

    @Column(name = "gender")
    private GenderEnum gender;

    @Column(name = "birth_year")
    private String birthYear;

    @JoinColumn(name = "planet_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private PlanetEntity planet;

    @JoinColumn(name = "specie_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private SpecieEntity specie;

}
