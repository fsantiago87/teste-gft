package gft.teste.contabilidade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContabilidadeType {

    DEBITO("D"),
    CREDITO("C");

    private final String tipoLancamento;
}
