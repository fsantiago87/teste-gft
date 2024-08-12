package gft.teste.contabilidade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContabilidadeNotFoundException extends Exception {

    public ContabilidadeNotFoundException(String name) {
        super(String.format("Contabilidade with name %s not found in the system.", name));
    }

    public ContabilidadeNotFoundException(Long id) {
        super(String.format("Contabilidade with id %s not found in the system.", id));
    }
}
