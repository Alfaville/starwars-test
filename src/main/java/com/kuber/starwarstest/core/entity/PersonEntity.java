package com.kuber.starwarstest.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mass")
    private Double mass;

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
