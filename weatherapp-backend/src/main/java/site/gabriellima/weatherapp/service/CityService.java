package site.gabriellima.weatherapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.gabriellima.weatherapp.domain.City;
import site.gabriellima.weatherapp.dto.CityDTO;
import site.gabriellima.weatherapp.dto.CityForecastDTO;
import site.gabriellima.weatherapp.dto.CitySaveDTO;
import site.gabriellima.weatherapp.exception.DataDuplicationException;
import site.gabriellima.weatherapp.exception.ObjectNotFoundException;
import site.gabriellima.weatherapp.repository.CityRepository;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final WeatherService weatherService;
    private final CacheManager cacheManager;

    @Autowired
    public CityService(CityRepository cityRepository,
                       WeatherService weatherService,
                       CacheManager cacheManager) {
        this.cityRepository = cityRepository;
        this.weatherService = weatherService;
        this.cacheManager = cacheManager;
    }

    @Cacheable("cities")
    public Page<CityDTO> findAll(String search, Pageable pageable) {
        return cityRepository.findAll(search, pageable).map(CityDTO::new);
    }

    @Cacheable("forecast-by-city-id")
    public CityForecastDTO findCityForecasts(Long id) {
        City city = findCityById(id);
        int days = 5;
        return weatherService.getForecastByCityName(city.getName(), days);
    }

    public City findCityById(Long id) {
        return cityRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("City id %d not found", id)));
    }

    public CityDTO save(CitySaveDTO citySaveDTO) {
        CityForecastDTO cityForecastDTO = weatherService.getForecastByCityName(citySaveDTO.getName(), 1);
        if (cityRepository.existsByName(cityForecastDTO.getCity().getName())) {
            throw new DataDuplicationException(String.format("City %s already registered", cityForecastDTO.getCity().getName()));
        }

        City city = City.builder()
                .name(cityForecastDTO.getCity().getName())
                .country(cityForecastDTO.getCity().getCountry())
                .build();

        clearCacheCities();

        return new CityDTO(cityRepository.save(city));
    }

    private void clearCacheCities() {
        Cache cache = this.cacheManager.getCache("cities");
        if (cache != null) {
            cache.clear();
        }
    }
}
