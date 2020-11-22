package site.gabriellima.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gabriellima.weatherapp.domain.City;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String name;
    private String country;

    public CityDTO(City city) {
        id = city.getId();
        name = city.getName();
        country = city.getCountry();
    }
}
