package site.gabriellima.weatherapp.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CityForecastDTO {

    private final CityDTO city;
    private final List<ForecastDTO> forecasts = new ArrayList<>();

    public void addForecast(ForecastDTO forecastDTO) {
        this.forecasts.add(forecastDTO);
    }
}
