package com.kuber.starwarstest.com.kuber.starwarstest.dataprovider.db;

import com.kuber.starwarstest.core.entity.GenderEnum;
import com.kuber.starwarstest.core.entity.PersonEntity;
import com.kuber.starwarstest.dataprovider.repository.PersonRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integrated test Database - PostgreSQL")
public class DatabaseIntegrationTest {

    public static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer<>("postgres:9.6")
            .withDatabaseName("startwars")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432)
            ;

    @Autowired
    private PersonRepository personRepository;

    @BeforeAll
    public static void startMockServer() {
        POSTGRES.start();
        System.setProperty("spring.datasource.url", POSTGRES.getJdbcUrl());
    }

    @AfterAll
    public static void stop() {
        POSTGRES.stop();
    }

    @Test
    @Order(1)
    @DisplayName("Insert a new person test success")
    public void insert_a_new_person_test_success() {
        PersonEntity person = new PersonEntity();
        person.setId(2L);
        person.setName("Luke Skywalker");
        person.setGender(GenderEnum.MALE);
        person.setBirthYear("19BBY");
        person.setSkinColor("fair");
        person.setHairColor("blond");
        person.setEyeColor("blue");
        person.setHeight(172);
        person.setMass(77.0);

        final PersonEntity savedPerson = personRepository.save(person);
        assertNotNull(savedPerson.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Find person by ID - Test success")
    public void find_person_by_id_test_success() {
        Optional<PersonEntity> personOp = personRepository.findById(1L);
        assertNotNull(personOp.get());

        final PersonEntity person = personOp.get();
        assertEquals(1L, person.getId());
    }

    @Test
    @Order(3)
    @DisplayName("Find All person - Test success")
    public void find_all_person_test_success() {
        Iterable<PersonEntity> personIterable = personRepository.findAll();
        List<PersonEntity> personList = StreamSupport.stream(personIterable.spliterator(), false)
                .collect(Collectors.toList());

        assertFalse(personList.isEmpty());
        assertEquals(2 , personList.size());
    }

}
