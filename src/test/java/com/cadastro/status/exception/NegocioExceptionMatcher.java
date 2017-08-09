package com.cadastro.status.exception;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.cadastro.status.excecoes.NegocioException;

public class NegocioExceptionMatcher extends TypeSafeMatcher<NegocioException> {

	private String recebido;
	private String esperado;

	private NegocioExceptionMatcher(String esperado) {
		this.esperado = esperado;
	}

	public static NegocioExceptionMatcher validarMensagem(String esperado) {
		return new NegocioExceptionMatcher(esperado);
	}

	@Override
	protected boolean matchesSafely(NegocioException exception) {
		recebido = exception.getMessage();
		return recebido.equals(esperado);
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText("recebido =").appendValue(recebido).appendText(" Esperado =").appendValue(esperado);

	}
}
