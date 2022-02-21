package ro.fasttrackit;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CountryService {
    private final List<Country> countries = new ArrayList<>();

    public CountryService(CountryReader countryReader) {
        countries.addAll(countryReader.getCountries());
    }

    public List<Country> getAllCountries() {
        return countries;
    }

    public List<String> getCountryNames() {
        return countries.stream()
                .map(country -> country.name())
                .toList();
    }

    public String getCapitalForCountry(int id) {
        return countries.stream()
                .filter(country -> country.id() == id)
                .map(country -> country.capital())
                .findFirst()
                .orElse("Id not found");
    }

    public long getPopulationForCountry(int id) {
        return countries.stream()
                .filter(country -> country.id() == id)
                .map(country -> country.population())
                .findFirst()
                .orElse((long) -1);
    }

    public List<Country> getCountriesInContinent(String continentName) {
        return countries.stream()
                .filter(country -> country.continent().equalsIgnoreCase(continentName))
                .toList();
    }

    public List<String> getCountryNeighbours(int id) {
        return countries.stream()
                .filter(country -> country.id() == id)
                .map(country -> country.neighbours())
                .findFirst()
                .orElse(Collections.emptyList());
    }

    public List<Country> getCountriesInContinentWithPopulationLargerThan(String continentName, long population) {
        return getCountriesInContinent(continentName).stream()
                .filter(country -> country.population() > population)
                .toList();
    }

    public List<Country> getByNeighbours(String include, String exclude) {
        return countries.stream()
                .filter(country -> country.neighbours().contains(include)
                        && !country.neighbours().contains(exclude))
                .toList();
    }
}