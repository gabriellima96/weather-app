package site.gabriellima.weatherapp.dto;

import site.gabriellima.weatherapp.domain.City;

public class CityDTO {

    private Long id;
    private String name;
    private String country;

    public CityDTO() {

    }

    public CityDTO(City city) {
        id = city.getId();
        name = city.getName();
        country = city.getCountry();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
