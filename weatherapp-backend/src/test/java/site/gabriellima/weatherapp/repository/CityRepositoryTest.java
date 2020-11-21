package site.gabriellima.weatherapp.repository;

import helper.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import site.gabriellima.weatherapp.domain.City;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CityRepository repository;

    @Test
    @DisplayName("Deve salvar uma cidade")
    void shouldSaveCity() {
        City city = TestHelper.newCity();
        city.setId(null);

        repository.save(city);

        assertThat(city.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve buscar uma cidade por id")
    void shouldFindCityById() {
        City city = TestHelper.newCity();
        city.setId(null);
        entityManager.persist(city);

        Optional<City> cityOptional = repository.findById(city.getId());

        assertThat(cityOptional).isPresent();
        assertThat(cityOptional.get().getId()).isEqualTo(city.getId());
        assertThat(cityOptional.get().getName()).isEqualTo(city.getName());
        assertThat(cityOptional.get().getCountry()).isEqualTo(city.getCountry());
    }

    @Test
    @DisplayName("Deve retornar um optional vazio ao buscar uma cidade por id não existente")
    void shouldOptionalEmptyFindCityByIdNotFound() {
        Long invalidId = 50L;
        Optional<City> cityOptional = repository.findById(invalidId);

        assertThat(cityOptional).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar dataIntegratyViolationException ao tentar salvar com name null")
    void shouldReturnDataIntegratyViolationExceptionSaveCityWithNameNull() {
        City city = TestHelper.newCity();
        city.setName(null);

        assertThatThrownBy(() -> {
            repository.save(city);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }
}
