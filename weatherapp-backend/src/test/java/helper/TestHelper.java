package helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import site.gabriellima.weatherapp.domain.City;
import site.gabriellima.weatherapp.dto.CityDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class TestHelper {
    private static final Random RANDOM = new Random();

    public static City newCity() {
        City city = new City();
        city.setId(Math.abs(RANDOM.nextLong()));
        city.setName(UUID.randomUUID().toString());
        city.setCountry(UUID.randomUUID().toString());

        return city;
    }

    public static Page<CityDTO> newPageCity(Integer number) {
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            cities.add(newCity());
        }
        return new PageImpl<>(cities, PageRequest.of(0, number), cities.size()).map(CityDTO::new);
    }
}
