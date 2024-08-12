package gft.teste.contabilidade.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.PathVariable;

import gft.teste.contabilidade.dto.ContabilidadeDTO;
import gft.teste.contabilidade.exception.ContabilidadeAlreadyRegisteredException;
import gft.teste.contabilidade.exception.ContabilidadeNotFoundException;

import java.util.List;

@Api("Manage Contabilidade")
public interface ContabilidadeControllerDocs {

    @ApiOperation(value = "Contabilidade creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success Contabilidade creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    ContabilidadeDTO novoLancamento(ContabilidadeDTO contabilidadeDTO) throws ContabilidadeAlreadyRegisteredException;

    @ApiOperation(value = "Returns Contabilidade found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Contabilidade found in the system"),
            @ApiResponse(code = 404, message = "Contabilidade with given name not found.")
    })
    ContabilidadeDTO findByNome(@PathVariable String nome) throws ContabilidadeNotFoundException;

    @ApiOperation(value = "Returns a list of all Contabilidade registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all Contabilidade registered in the system"),
    })
    List<ContabilidadeDTO> listLancamentos();

    @ApiOperation(value = "Delete a Contabilidade found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success Contabilidade deleted in the system"),
            @ApiResponse(code = 404, message = "Contabilidade with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws ContabilidadeNotFoundException;
}
