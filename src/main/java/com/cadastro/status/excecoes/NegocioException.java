/**
 * 
 */
package com.cadastro.status.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção que é lançada em falha de validação nas regras de negócio da aplicação<br>
 * Ela já implementa o codigo HTTP da aplicação para lançar diretamente para o usuario a mensagem de erro
 * 
 * @author Daniel Ferraz
 * @since 5 de ago de 2017
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class NegocioException  extends RuntimeException{

	public NegocioException(String message) {
		super(message);
	}
	
}
