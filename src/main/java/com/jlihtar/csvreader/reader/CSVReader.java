package com.jlihtar.csvreader.reader;

import com.jlihtar.csvreader.model.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static String TYPE = "text/csv";
    static String[] headers = {"Ime", "Prezime", "Datum rodjenja"};

    public static boolean hasCSVFormat(MultipartFile file){
        if(TYPE.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel")){
            return true;
        }
        return false;
    }

    public static List<Person> csvToPersons(InputStream inputStream){
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<Person> listOfPersons = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords){
                Person person = new Person(
                        csvRecord.get("Ime"),
                        csvRecord.get("Prezime"),
                        LocalDate.parse(csvRecord.get("DatumRodjenja"))

                );
                listOfPersons.add(person);
            }
            return listOfPersons;

        } catch (IOException e) {
            throw new RuntimeException("Greška prilikom čitanja datoteke: " + e.getMessage());
        }
    }
}
