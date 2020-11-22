package site.gabriellima.weatherapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import site.gabriellima.weatherapp.dto.CityDTO;
import site.gabriellima.weatherapp.dto.CityForecastDTO;
import site.gabriellima.weatherapp.dto.CitySaveDTO;
import site.gabriellima.weatherapp.service.CityService;

import javax.validation.Valid;
import java.net.URI;

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
        return ResponseEntity.ok(cityService.findAll(search, pageable));
    }

    @GetMapping("/{id}/forecasts")
    public ResponseEntity<CityForecastDTO> findCityForecasts(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.findCityForecasts(id));
    }

    @PostMapping
    public ResponseEntity<CityDTO> save(@Valid @RequestBody CitySaveDTO citySaveDTO) {
        CityDTO cityDTO = cityService.save(citySaveDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cityDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(cityDTO);
    }
}
