package com.test.core.parser.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.core.exception.ParserException;
import com.test.core.parser.PersonParser;
import com.test.domain.Person;
import com.test.domain.enumeration.ColumnHeader;
import com.test.domain.enumeration.Gender;

public class DefaultPersonParser implements PersonParser {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String LINE_SPLIT_REGEX = ",";
	private static final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public List<Person> parsePerson(File file) throws ParserException {
        List<Person> persons = new ArrayList<Person>();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            Map<Integer, ColumnHeader> columnHeaderMap = processHeaderLine(line);

            while ((line = bufferedReader.readLine()) != null) {
                Person person = createPerson(columnHeaderMap, line);
                if (persons.contains(person)) {
                    System.out.println("Duplicate person found! Skipping " + person);
				} else {
					persons.add(person);
				}
            }

        } catch (IOException e) {
            throw new ParserException("Error parsing input file", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("Buffered reader was not closed. Reason: " + e.getMessage());
                }
            }
        }
		return persons;
    }

    private Map<Integer, ColumnHeader> processHeaderLine(String line) {
        Map<Integer, ColumnHeader> headersMap = new HashMap<Integer, ColumnHeader>();

        String[] headers = line.split(LINE_SPLIT_REGEX);
        for (int i = 0; i < headers.length; i++) {
            ColumnHeader columnHeader = ColumnHeader.getColumnHeaderByName(headers[i].trim());
            headersMap.put(i, columnHeader);
        }

        return headersMap;
    }

    private Person createPerson(Map<Integer, ColumnHeader> headers, String line) throws ParserException {
        Person person = new Person();

        String[] columns = line.split(LINE_SPLIT_REGEX);
        for (int i = 0; i < columns.length; i++) {
            ColumnHeader columnHeader = headers.get(i);
            columns[i] = columns[i].trim();
            
            switch (columnHeader) {
                case FULL_NAME: buildFullName(person, columns[i]); break;
                case GENDER: buildGender(person, columns[i]); break;
                case AGE: buildAge(person, columns[i]); break;
                case DATE_OF_BIRTH: buildDateOfBirth(person, columns[i]); break;
            }
        }

        return person;
    }

    private void buildFullName(Person person, String fullName) {
        person.setFullName(fullName);
    }

    private void buildGender(Person person, String gender) throws ParserException {
        try {
            person.setGender(Gender.getGenderByName(gender));
        } catch (IllegalArgumentException e) {
            throw new ParserException("Can not parse gender value - " + gender, e);
        }
    }

    private void buildAge(Person person, String age) throws ParserException {
        try {
            person.setAge(Integer.parseInt(age));
        } catch (NumberFormatException e) {
            throw new ParserException("Can not parse age value - " + age, e);
        }
    }

    private void buildDateOfBirth(Person person, String dateOfBirth) throws ParserException {
        try {
            person.setDateOfBirth(formatter.parse(dateOfBirth));
        } catch (ParseException e) {
            throw new ParserException("Can not parse date value - " + dateOfBirth, e);
        }
    }
}
