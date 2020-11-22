package site.gabriellima.weatherapp.service.weatherapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ForecastDTO {

    List<ForecastDayDTO> forecastday = new ArrayList<>();
}
