package gft.teste.contabilidade.service;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gft.teste.contabilidade.dto.ContabilidadeDTO;
import gft.teste.contabilidade.entity.Contabilidade;
import gft.teste.contabilidade.exception.ContabilidadeAlreadyRegisteredException;
import gft.teste.contabilidade.exception.ContabilidadeNotFoundException;
import gft.teste.contabilidade.mapper.ContabilidadeMapper;
import gft.teste.contabilidade.repository.ContabilidadeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContabilidadeService {

    private final ContabilidadeRepository contabilidadeRepository;
    private final ContabilidadeMapper contabilidadeMapper = ContabilidadeMapper.INSTANCE;

    public ContabilidadeDTO novoLancamento(ContabilidadeDTO contabilidadeDTO) throws ContabilidadeAlreadyRegisteredException {
        Contabilidade contabilidade = contabilidadeMapper.toModel(contabilidadeDTO);
        Contabilidade savedContabilidade = contabilidadeRepository.save(contabilidade);
        return contabilidadeMapper.toDTO(savedContabilidade);
    }

    public ContabilidadeDTO findByNome(String nome) throws ContabilidadeNotFoundException {
        Contabilidade foundContabilidade = contabilidadeRepository.findByNome(nome)
                .orElseThrow(() -> new ContabilidadeNotFoundException(nome));
        return contabilidadeMapper.toDTO(foundContabilidade);
    }

    public List<ContabilidadeDTO> listLancamentos() {
        return contabilidadeRepository.findAll()
                .stream()
                .map(contabilidadeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws ContabilidadeNotFoundException {
        verifyIfExists(id);
        contabilidadeRepository.deleteById(id);
    }

    private Contabilidade verifyIfExists(Long id) throws ContabilidadeNotFoundException {
        return contabilidadeRepository.findById(id)
                .orElseThrow(() -> new ContabilidadeNotFoundException(id));
    }

}
