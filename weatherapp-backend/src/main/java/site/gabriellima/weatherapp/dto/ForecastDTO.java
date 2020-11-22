package site.gabriellima.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastDTO {

    private LocalDate date;
    private Float tempMin;
    private Float tempMax;
    private String conditionText;
    private String conditionIconUrl;
}
