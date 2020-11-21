package site.gabriellima.weatherapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.gabriellima.weatherapp.domain.City;
import site.gabriellima.weatherapp.repository.CityRepository;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Page<City> findAll(String search, Pageable pageable) {
        return cityRepository.findAll(search, pageable);
    }
}
