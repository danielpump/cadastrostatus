/**
 * 
 */
package com.cadastro.status.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exce��o que � lan�ada em falha de valida��o nas regras de neg�cio da aplica��o<br>
 * Ela j� implementa o codigo HTTP da aplica��o para lan�ar diretamente para o usuario a mensagem de erro
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
