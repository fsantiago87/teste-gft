package gft.teste.contabilidade.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import gft.teste.contabilidade.builder.ContabilidadeDTOBuilder;
import gft.teste.contabilidade.dto.ContabilidadeDTO;
import gft.teste.contabilidade.exception.ContabilidadeNotFoundException;
import gft.teste.contabilidade.service.ContabilidadeService;

import java.util.Collections;

import static gft.teste.contabilidade.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ContabilidadeControllerTest {

    private static final String CONTABILIDADE_API_URL_PATH = "/api/v1/contabilidade";
    private static final long INVALID_CONTABILIDADE_ID = 2l;

    private MockMvc mockMvc;

    @Mock
    private ContabilidadeService contabilidadeService;

    @InjectMocks
    private ContabilidadeController contabilidadeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contabilidadeController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAContabilidadeIsCreated() throws Exception {
        //given
        ContabilidadeDTO contabilidadeDTO = ContabilidadeDTOBuilder.builder().build().toContabilidadeDTO();

        //when
        when(contabilidadeService.novoLancamento(contabilidadeDTO)).thenReturn(contabilidadeDTO);

        //then
        mockMvc.perform(post(CONTABILIDADE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(contabilidadeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is(contabilidadeDTO.getNome())))
                .andExpect(jsonPath("$.tipoLancamento", is(contabilidadeDTO.getTipoLancamento().toString())));
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        //given
        ContabilidadeDTO contabilidadeDTO = ContabilidadeDTOBuilder.builder().build().toContabilidadeDTO();

        //when
        when(contabilidadeService.findByNome(contabilidadeDTO.getNome())).thenReturn(contabilidadeDTO);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(CONTABILIDADE_API_URL_PATH + "/" + contabilidadeDTO.getNome())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(contabilidadeDTO.getNome())))               
                .andExpect(jsonPath("$.tipoLancamento", is(contabilidadeDTO.getTipoLancamento().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        //given
        ContabilidadeDTO contabilidadeDTO = ContabilidadeDTOBuilder.builder().build().toContabilidadeDTO();

        //when
        when(contabilidadeService.findByNome(contabilidadeDTO.getNome())).thenThrow(ContabilidadeNotFoundException.class);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(CONTABILIDADE_API_URL_PATH + "/" + contabilidadeDTO.getNome())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithContabilidadesIsCalledThenOkStatusIsReturned() throws Exception {
        //given
        ContabilidadeDTO contabilidadeDTO = ContabilidadeDTOBuilder.builder().build().toContabilidadeDTO();

        //when
        when(contabilidadeService.listLancamentos()).thenReturn(Collections.singletonList(contabilidadeDTO));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(CONTABILIDADE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is(contabilidadeDTO.getNome())))
                .andExpect(jsonPath("$[0].tipoLancamento", is(contabilidadeDTO.getTipoLancamento().toString())));
    }

    @Test
    void whenGETListWithoutContabilidadesIsCalledThenOkStatusIsReturned() throws Exception {
        //given
        ContabilidadeDTO contabilidadeDTO = ContabilidadeDTOBuilder.builder().build().toContabilidadeDTO();

        //when
        when(contabilidadeService.listLancamentos()).thenReturn(Collections.singletonList(contabilidadeDTO));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(CONTABILIDADE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        //given
        ContabilidadeDTO contabilidadeDTO = ContabilidadeDTOBuilder.builder().build().toContabilidadeDTO();

        //when
        doNothing().when(contabilidadeService).deleteById(contabilidadeDTO.getId());

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(CONTABILIDADE_API_URL_PATH + "/" + contabilidadeDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(ContabilidadeNotFoundException.class).when(contabilidadeService).deleteById(INVALID_CONTABILIDADE_ID);

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(CONTABILIDADE_API_URL_PATH + "/" + INVALID_CONTABILIDADE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
