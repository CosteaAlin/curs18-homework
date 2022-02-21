package ro.fasttrackit;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CountryReader {

    public List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/countries2.txt"))) {
            String line;
            int id = 0;
            while ((line = reader.readLine()) != null) {
                countries.add(readCountryFromLine(id++, line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countries;
    }

    private Country readCountryFromLine(int id, String line) {
        String[] tokens = line.split("[|]");
        long population = Long.parseLong(tokens[2]);
        int area = Integer.parseInt(tokens[3]);
        List<String> neighbours = new ArrayList<>();
        if (tokens.length == 6) {
            neighbours = new ArrayList<>(Arrays.asList(tokens[5].split("[~]")));
        }
        return new Country(id, tokens[0], tokens[1], population,
                area, tokens[4], neighbours);
    }
}
