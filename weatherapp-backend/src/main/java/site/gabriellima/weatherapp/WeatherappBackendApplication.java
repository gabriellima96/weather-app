package site.gabriellima.weatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WeatherappBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherappBackendApplication.class, args);
    }
}
