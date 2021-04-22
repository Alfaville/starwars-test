package com.kuber.starwarstest.dataprovider.repository;

import com.kuber.starwarstest.core.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
}
