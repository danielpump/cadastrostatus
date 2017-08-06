/**
 * 
 */
package com.cadastro.status.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
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
