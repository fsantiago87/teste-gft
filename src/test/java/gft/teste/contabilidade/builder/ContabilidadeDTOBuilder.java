package gft.teste.contabilidade.builder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import gft.teste.contabilidade.dto.ContabilidadeDTO;
import gft.teste.contabilidade.enums.ContabilidadeType;
import lombok.Builder;

@Builder
public class ContabilidadeDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String nome = "Salarios";

    @Builder.Default
    private double valor = 0;

    @Builder.Default
    private ContabilidadeType tipoLancamento = ContabilidadeType.DEBITO;

    public ContabilidadeDTO toContabilidadeDTO() {
        return new ContabilidadeDTO(id,
                nome,          
                valor,
                tipoLancamento);
    }
}
