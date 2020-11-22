package site.gabriellima.weatherapp.service.weatherapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import site.gabriellima.weatherapp.dto.CityDTO;
import site.gabriellima.weatherapp.dto.CityForecastDTO;
import site.gabriellima.weatherapp.dto.ForecastDTO;
import site.gabriellima.weatherapp.exception.WeatherException;
import site.gabriellima.weatherapp.service.WeatherService;
import site.gabriellima.weatherapp.service.weatherapi.dto.ForecastDayDTO;
import site.gabriellima.weatherapp.service.weatherapi.dto.LocationDTO;
import site.gabriellima.weatherapp.service.weatherapi.dto.WeatherAPIDTO;

@Slf4j
@Component
public class WeatherApiService implements WeatherService {

    private static final String MESSAGE_LOG_ERROR = "An error occurred while fetching forecasts city {}: {}";

    private final String key;
    private final String url;

    public WeatherApiService(@Value("${weather-api.key}") String key,
                             @Value("${weather-api.url}") String url) {
        this.key = key;
        this.url = url;
    }

    @Override
    public CityForecastDTO getForecastByCityName(String name, Integer days) {
        try {
            final String lang = "pt";
            ResponseEntity<WeatherAPIDTO> response = createRestTemplate().getForEntity(String.format("%s?lang=%s&key=%s&days=%s&q=%s", url, lang, key, days, name), WeatherAPIDTO.class);
            return toCityForecastDTO(response.getBody());
        } catch (HttpStatusCodeException e) {
            log.error(MESSAGE_LOG_ERROR, name, e.getResponseBodyAsString());
            throw new WeatherException(String.format("Forecasts not found: %s", name));
        } catch (RestClientException e) {
            log.error(MESSAGE_LOG_ERROR, name, e.getMessage());
            throw new WeatherException("Forecast not available. information client unavailable");
        }
    }

    public CityForecastDTO toCityForecastDTO(WeatherAPIDTO weatherDTO) {
        if (weatherDTO == null) return null;

        var cityForecast = CityForecastDTO.builder()
                .city(toCityDTO(weatherDTO.getLocation()))
                .build();

        weatherDTO.getForecast()
                .getForecastday()
                .forEach(forecastDay -> cityForecast.addForecast(toForecastDTO(forecastDay)));

        return cityForecast;
    }

    public CityDTO toCityDTO(LocationDTO location) {
        return CityDTO.builder()
                .name(location.getName())
                .country(location.getCountry())
                .build();
    }

    public ForecastDTO toForecastDTO(ForecastDayDTO forecastDay) {
        return ForecastDTO.builder()
                .date(forecastDay.getDate())
                .tempMin(forecastDay.getDay().getMintempC())
                .tempMax(forecastDay.getDay().getMaxtempC())
                .conditionText(forecastDay.getDay().getCondition().getText())
                .conditionIconUrl("https:" + forecastDay.getDay().getCondition().getIcon())
                .build();
    }

    private RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
