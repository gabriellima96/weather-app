package site.gabriellima.weatherapp.exception;

public class DataDuplicationException extends RuntimeException {
    public DataDuplicationException(String message) {
        super(message);
    }
}
