package site.gabriellima.weatherapp.service.weatherapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForecastDayDTO {

    private LocalDate date;
    private DayDTO day;
}
