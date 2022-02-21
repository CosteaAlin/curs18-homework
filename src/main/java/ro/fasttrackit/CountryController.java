package ro.fasttrackit;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("countries")
    List<Country> getCountries(@RequestParam(required = false) String includeNeighbour,
                               @RequestParam(required = false) String excludeNeighbour) {
        if (includeNeighbour != null || excludeNeighbour != null) {
            return countryService.getByNeighbours(includeNeighbour, excludeNeighbour);
        }
        return countryService.getAllCountries();
    }

    @GetMapping("countries/name")
    List<String> getCountryNames() {
        return countryService.getCountryNames();
    }

    @GetMapping("countries/{countryId}/capital")
    String getCapitalForCountry(@PathVariable int countryId) {
        return countryService.getCapitalForCountry(countryId);
    }

    @GetMapping("countries/{countryId}/population")
    long getPopulationForCountry(@PathVariable int countryId) {
        return countryService.getPopulationForCountry(countryId);
    }

    @GetMapping("continents/{continentName}/countries")
    List<Country> getCountriesInContinent(@PathVariable String continentName,
                                          @RequestParam(required = false) Optional<Long> minPopulation) {
        if (continentName != null && minPopulation.isPresent()) {
            return countryService.getCountriesInContinentWithPopulationLargerThan(continentName, minPopulation.get());
        } else if (continentName != null && !minPopulation.isPresent()) {
            return countryService.getCountriesInContinent(continentName);
        }
        return new ArrayList<>();
    }

    @GetMapping("countries/{countryId}/neighbours")
    List<String> getCountryNeighbours(@PathVariable int countryId) {
        return countryService.getCountryNeighbours(countryId);
    }
}
