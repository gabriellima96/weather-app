package site.gabriellima.weatherapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.gabriellima.weatherapp.dto.CityDTO;
import site.gabriellima.weatherapp.service.CityService;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<Page<CityDTO>> findAll(@RequestParam(required = false, value = "search") String search, Pageable pageable) {
        return ResponseEntity.ok(cityService.findAll(search, pageable).map(CityDTO::new));
    }
}
