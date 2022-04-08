package com.jlihtar.csvreader.service;

import com.jlihtar.csvreader.model.Person;
import com.jlihtar.csvreader.reader.CSVReader;
import com.jlihtar.csvreader.repository.OsobaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class CSVService {

    private static Logger logger = LoggerFactory.getLogger(CSVService.class);

    @Autowired
    OsobaRepository repository;

    public void savePersons(MultipartFile file) throws IOException {
        try{
            logger.info("Loading csv file.");
            List<Person> listOfPersons = CSVReader.csvToPersons(file.getInputStream());
            logger.info("Loading csv file completed.");
            logger.info("Saving persons in database.");
            repository.saveAll(listOfPersons);
            logger.info("Saving persons in database completed");
        } catch (IOException e) {
            logger.error("Error saving data from file!");
            throw new RuntimeException(("Greška prilikom spremanja podataka iz datoteke: " + e.getMessage()));

        }
    }

    public List<Person> getAllPersons(){
        logger.info("Retrieving all persons from database.");
        return repository.findAll();
    }
}
