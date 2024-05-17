package com.example.summarize.config;

import com.example.summarize.model.Person;
import com.example.summarize.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Configuration
public class DataLoader {

    private final PersonRepository personRepository;

    @Autowired
    public DataLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

@Bean
CommandLineRunner loadData() {
    return args -> {
            boolean isHeaderRow = true;  // Pula o cabeçalho
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
            Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("lista.csv")))))
        {
            String line;
            while ((line = br.readLine()) != null) {
                if (isHeaderRow) {
                    isHeaderRow = false;
                    continue;
                }
                String[] fields = line.split(",");
                // Verifica os campos
                if(fields.length < 9)
                    throw new IllegalArgumentException("Invalid line: " + line);

                Person person = new Person();
                person.setGender(fields[0]);
                person.setTitle(fields[1]);
                person.setGivenName(fields[2]);
                person.setMiddleInitial(fields[3]);
                person.setSurname(fields[4]);
                person.setState(fields[5]);
                person.setEmail(fields[6]);
                person.setBirthday((fields[7]));
                person.setLatitude(Double.valueOf(fields[8]));
                person.setLongitude(Double.valueOf(fields[9]));

                // Salva a pessoa
                personRepository.save(person);
            }

        } catch (IOException e) {
            // Exceção
            e.printStackTrace();
        } catch(Exception e) {
            // Exceção
            e.printStackTrace();
        }
    };
}
}
