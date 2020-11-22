package site.gabriellima.weatherapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ValidationError extends ResponseError {

    private final List<FieldError> errors = new ArrayList<>();

    public ValidationError(String error, String message) {
        super(error, message);
    }

    public void addFieldError(String name, String error) {
        this.errors.add(new FieldError(name, error));
    }
}
