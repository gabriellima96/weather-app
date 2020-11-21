package site.gabriellima.weatherapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.gabriellima.weatherapp.domain.City;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE :search is null or (lower(c.name) LIKE concat('%',lower(:search),'%') or lower(c.country) LIKE concat('%',lower(:search),'%'))")
    Page<City> findAll(@Param("search") String search, Pageable pageable);
}
