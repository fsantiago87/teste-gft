package gft.teste.contabilidade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import gft.teste.contabilidade.enums.ContabilidadeType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContabilidadeDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String nome;

    @NotNull
    private double valor;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ContabilidadeType tipoLancamento;
}
