package com.jlihtar.csvreader.controller;


import com.jlihtar.csvreader.model.Person;
import com.jlihtar.csvreader.reader.CSVReader;
import com.jlihtar.csvreader.response.ResponseMessage;
import com.jlihtar.csvreader.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("http://localhost:8080")
    @Controller
    @RequestMapping("/csvreader")
    public class CSVController {

        @Autowired
        CSVService fileService;

        @PostMapping("/upload")
        public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
            String message = "";

            if (CSVReader.hasCSVFormat(file)) {
                try {
                    fileService.savePersons(file);

                    message = "Uspješno učitana datoteka: " + file.getOriginalFilename();

                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
                } catch (Exception e) {
                    message = "Nije moguće učitati datoteku: " + file.getOriginalFilename();
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
                }
            }
            message = "Molim Vas, učitajte csv datoteku!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }

        @GetMapping("/persons")
        public ResponseEntity<List<Person>> getAllPersons() {
            try {
                List<Person> persons = fileService.getAllPersons();

                if (persons.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(persons, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

