package gft.teste.contabilidade.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gft.teste.contabilidade.builder.ContabilidadeDTOBuilder;
import gft.teste.contabilidade.dto.ContabilidadeDTO;
import gft.teste.contabilidade.entity.Contabilidade;
import gft.teste.contabilidade.exception.ContabilidadeNotFoundException;
import gft.teste.contabilidade.mapper.ContabilidadeMapper;
import gft.teste.contabilidade.repository.ContabilidadeRepository;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContabilidadeServiceTest {


    @Mock
    private ContabilidadeRepository contabilidadeRepository;

    private ContabilidadeMapper contabilidadeMapper = ContabilidadeMapper.INSTANCE;

    @InjectMocks
    private ContabilidadeService contabilidadeService;

    @Test
    void whenValidContabilidadeNameIsGivenThenReturnAContabilidade() throws ContabilidadeNotFoundException {
        // given
        ContabilidadeDTO expectedFoundContabilidaderDTO = ContabilidadeDTOBuilder.builder().build().toContabilidadeDTO();
        Contabilidade expectedFoundContabilidade = contabilidadeMapper.toModel(expectedFoundContabilidaderDTO);

        // when
        when(contabilidadeRepository.findByNome(expectedFoundContabilidade.getNome())).thenReturn(Optional.of(expectedFoundContabilidade));

        // then
        ContabilidadeDTO foundContabilidadeDTO = contabilidadeService.findByNome(expectedFoundContabilidaderDTO.getNome());

        assertThat(foundContabilidadeDTO, is(equalTo(expectedFoundContabilidaderDTO)));
    }

    @Test
    void whenNotRegisteredContabilidadeNameIsGivenThenThrowAnException() {
        // given
        ContabilidadeDTO expectedFoundContabilidadeDTO = ContabilidadeDTOBuilder.builder().build().toContabilidadeDTO();

        // when
        when(contabilidadeRepository.findByNome(expectedFoundContabilidadeDTO.getNome())).thenReturn(Optional.empty());

        // then
        assertThrows(ContabilidadeNotFoundException.class, () -> contabilidadeService.findByNome(expectedFoundContabilidadeDTO.getNome()));
    }

    @Test
    void whenListContabilidadeIsCalledThenReturnAListOfContabilidade() {
        // given
        ContabilidadeDTO expectedFoundContabilidadeDTO = ContabilidadeDTOBuilder.builder().build().toContabilidadeDTO();
        Contabilidade expectedFoundContabilidade = contabilidadeMapper.toModel(expectedFoundContabilidadeDTO);

        //when
        when(contabilidadeRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundContabilidade));

        //then
        List<ContabilidadeDTO> foundListContabilidadeDTO = contabilidadeService.listLancamentos();

        assertThat(foundListContabilidadeDTO, is(not(empty())));
        assertThat(foundListContabilidadeDTO.get(0), is(equalTo(expectedFoundContabilidadeDTO)));
    }

    @Test
    void whenListContabilidadeIsCalledThenReturnAnEmptyListOfContabilidade() {
        //when
        when(contabilidadeRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<ContabilidadeDTO> foundListContabilidadeDTO = contabilidadeService.listLancamentos();

        assertThat(foundListContabilidadeDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAContabilidadeShouldBeDeleted() throws ContabilidadeNotFoundException{
        // given
        ContabilidadeDTO expectedDeletedContabilidadeDTO = ContabilidadeDTOBuilder.builder().build().toContabilidadeDTO();
        Contabilidade expectedDeletedContabilidade = contabilidadeMapper.toModel(expectedDeletedContabilidadeDTO);

        // when
        when(contabilidadeRepository.findById(expectedDeletedContabilidadeDTO.getId())).thenReturn(Optional.of(expectedDeletedContabilidade));
        doNothing().when(contabilidadeRepository).deleteById(expectedDeletedContabilidadeDTO.getId());

        // then
        contabilidadeService.deleteById(expectedDeletedContabilidadeDTO.getId());

        verify(contabilidadeRepository, times(1)).findById(expectedDeletedContabilidadeDTO.getId());
        verify(contabilidadeRepository, times(1)).deleteById(expectedDeletedContabilidadeDTO.getId());
    }

}
