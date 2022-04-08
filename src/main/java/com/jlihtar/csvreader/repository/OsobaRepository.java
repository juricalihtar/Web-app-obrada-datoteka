package com.jlihtar.csvreader.repository;

import com.jlihtar.csvreader.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OsobaRepository extends JpaRepository<Person, Long> {
}
