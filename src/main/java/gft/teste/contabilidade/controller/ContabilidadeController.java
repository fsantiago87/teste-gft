package gft.teste.contabilidade.controller;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gft.teste.contabilidade.dto.ContabilidadeDTO;
import gft.teste.contabilidade.exception.ContabilidadeAlreadyRegisteredException;
import gft.teste.contabilidade.exception.ContabilidadeNotFoundException;
import gft.teste.contabilidade.service.ContabilidadeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contabilidade")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContabilidadeController implements ContabilidadeControllerDocs {

    private final ContabilidadeService contabilidadeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContabilidadeDTO novoLancamento(@RequestBody @Valid ContabilidadeDTO contabilidadeDTO) throws ContabilidadeAlreadyRegisteredException {
        return contabilidadeService.novoLancamento(contabilidadeDTO);
    }

    @GetMapping("/{nome}")
    public ContabilidadeDTO findByNome(@PathVariable String nome) throws ContabilidadeNotFoundException {
        return contabilidadeService.findByNome(nome);
    }

    @GetMapping
    public List<ContabilidadeDTO> listLancamentos() {
        return contabilidadeService.listLancamentos();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws ContabilidadeNotFoundException {
        contabilidadeService.deleteById(id);
    }

}
