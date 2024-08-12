package gft.teste.contabilidade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContabilidadeAlreadyRegisteredException extends Exception{

    public ContabilidadeAlreadyRegisteredException(String name) {
        super(String.format("Contabilidade with name %s already registered in the system.", name));
    }
}
