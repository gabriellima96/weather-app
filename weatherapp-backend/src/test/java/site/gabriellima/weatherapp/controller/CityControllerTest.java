package site.gabriellima.weatherapp.controller;

import helper.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import site.gabriellima.weatherapp.service.CityService;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = CityController.class)
class CityControllerTest {

    private static final String BASE_PATH = "/api/v1/cities";

    @Autowired
    MockMvc mvc;

    @MockBean
    private CityService cityService;

    @Test
    @DisplayName("Deve buscar todas as cidades de forma páginada")
    void shouldFindAllCities() throws Exception {
        int page = 0;
        int size = 5;

        var pageRequest = PageRequest.of(page, size);
        var cityPage = TestHelper.newPageCity(size);

        given(cityService.findAll(null, pageRequest)).willReturn(cityPage);

        String query = String.format("?page=%d&size=%d", page, size);

        mvc.perform(get(BASE_PATH.concat(query))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(size)))
                .andExpect(jsonPath("totalElements").value(size))
                .andExpect(jsonPath("pageable.pageSize").value(size))
                .andExpect(jsonPath("pageable.pageNumber").value(page));
    }

    @Test
    @DisplayName("Deve filtrar pelo nome da cidade")
    void shouldFilterCityByName() throws Exception {
        int page = 0;
        int size = 1;

        var pageRequest = PageRequest.of(page, size);
        var cityPage = TestHelper.newPageCity(size);
        var search = cityPage.getContent().get(0).getName();

        given(cityService.findAll(search, pageRequest)).willReturn(cityPage);

        String query = String.format("?search=%s&page=%d&size=%d", search, page, size);

        mvc.perform(get(BASE_PATH.concat(query))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(size)))
                .andExpect(jsonPath("content[0].name").value(search))
                .andExpect(jsonPath("totalElements").value(size))
                .andExpect(jsonPath("pageable.pageSize").value(size))
                .andExpect(jsonPath("pageable.pageNumber").value(page));
    }

    @Test
    @DisplayName("Deve filtrar pelo país das cidades")
    void shouldFilterCityByCountry() throws Exception {
        int page = 0;
        int size = 1;

        var pageRequest = PageRequest.of(page, size);
        var cityPage = TestHelper.newPageCity(size);
        var search = cityPage.getContent().get(0).getCountry();

        given(cityService.findAll(search, pageRequest)).willReturn(cityPage);

        String query = String.format("?search=%s&page=%d&size=%d", search, page, size);

        mvc.perform(get(BASE_PATH.concat(query))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(size)))
                .andExpect(jsonPath("content[0].country").value(search))
                .andExpect(jsonPath("totalElements").value(size))
                .andExpect(jsonPath("pageable.pageSize").value(size))
                .andExpect(jsonPath("pageable.pageNumber").value(page));
    }

    @Test
    @DisplayName("Deve retornar um conteúdo vázio se no filtro não encontrar nenhuma cidade")
    void shouldFilterCityNotFound() throws Exception {
        int page = 0;
        int size = 1;

        var pageRequest = PageRequest.of(page, size);
        var search = "invalid_search";

        given(cityService.findAll(search, pageRequest)).willReturn(Page.empty());

        String query = String.format("?search=%s&page=%d&size=%d", search, page, size);

        mvc.perform(get(BASE_PATH.concat(query))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(0)))
                .andExpect(jsonPath("totalElements").value(0))
                .andExpect(jsonPath("empty").value(true));
    }

}