package site.gabriellima.weatherapp.service;

import org.springframework.stereotype.Service;
import site.gabriellima.weatherapp.dto.CityForecastDTO;

@Service
public interface WeatherService {

    CityForecastDTO getForecastByCityName(String name, Integer days);
}
