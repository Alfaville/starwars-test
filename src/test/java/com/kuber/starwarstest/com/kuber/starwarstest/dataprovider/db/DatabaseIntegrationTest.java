package com.kuber.starwarstest.com.kuber.starwarstest.dataprovider.db;

import com.kuber.starwarstest.com.kuber.starwarstest.MockFactory;
import com.kuber.starwarstest.config.property.PersonQueueProperty;
import com.kuber.starwarstest.core.entity.GenderEnum;
import com.kuber.starwarstest.core.entity.PersonEntity;
import com.kuber.starwarstest.core.usecase.impl.PutPeopleQueueUseCaseImpl;
import com.kuber.starwarstest.core.usecase.impl.SaveAsyncPeopleUseCaseImpl;
import com.kuber.starwarstest.dataprovider.repository.PersonRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("Integrated Test")
public class DatabaseIntegrationTest {

    public static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer<>("postgres:9.6")
            .withDatabaseName("startwars")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432)
            ;

    public static final RabbitMQContainer RABBITMQ_CONTAINER = new RabbitMQContainer("rabbitmq:3.7-management")
            .withQueue("person-queue")
            .withExposedPorts(5672)
            ;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PutPeopleQueueUseCaseImpl putPeopleQueueUseCase;

    @Autowired
    private SaveAsyncPeopleUseCaseImpl saveAsyncPeopleUseCase;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PersonQueueProperty personQueueProperty;

    @BeforeAll
    public static void startMockServer() {
        POSTGRES.start();
        System.setProperty("spring.datasource.url", POSTGRES.getJdbcUrl());

        RABBITMQ_CONTAINER.start();
        System.setProperty("spring.rabbitmq.host", RABBITMQ_CONTAINER.getContainerIpAddress());
        System.setProperty("spring.rabbitmq.port", RABBITMQ_CONTAINER.getMappedPort(5672).toString());
    }

    @AfterAll
    public static void stop() {
        POSTGRES.stop();
        RABBITMQ_CONTAINER.stop();
    }

    @Nested
    @DisplayName("Integration test with PostgreSQL database")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class DatabaseTest {
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
            assertEquals(3, personList.size());
        }
    }

    @Nested
    @DisplayName("Integration test with RabbitMQ")
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class RabbitMqTest {

        @Test
        @DisplayName("Put the message in the queue and save person queue with success")
        void putMessagePersonQueueWithSuccess() {
            putPeopleQueueUseCase.execute(MockFactory.getCompletePeopleResponse());
            await().until(() -> personRepository.findById(3L).isPresent());
            Optional<PersonEntity> personOp = personRepository.findById(3L);
            assertEquals(personOp.get().getId(), 3L);
        }
    }

}
