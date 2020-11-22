package site.gabriellima.weatherapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(WeatherException.class)
    public ResponseEntity<ResponseError> weatherException(WeatherException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ResponseError error = new ResponseError("Weather Client Error", e.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataDuplicationException.class)
    public ResponseEntity<ResponseError> dataDuplicationException(DataDuplicationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ResponseError error = new ResponseError("Data Duplication", e.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError("Validation Error", "Verify fields");
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ResponseError> objectNotFoundException(ObjectNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ResponseError error = new ResponseError("Not found", e.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> exception(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseError error = new ResponseError("Server Internal Error", e.getMessage());
        return ResponseEntity.status(status).body(error);
    }
}
