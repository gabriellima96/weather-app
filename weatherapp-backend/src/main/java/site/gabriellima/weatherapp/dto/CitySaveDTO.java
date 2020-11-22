package site.gabriellima.weatherapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CitySaveDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
}
