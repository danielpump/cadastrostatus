package com.cadastro.status;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.cadastro.status.controles.PlacaCarroControle;
import com.cadastro.status.modelo.PlacaCarro;

@RunWith(SpringRunner.class)
public class PlacaCarroTest extends ApplicationTest {

	@Autowired
	private PlacaCarroControle controle;

	@Test
	public void teste() {
		PlacaCarro buscarPorNumero = controle.buscarPorNumero("FFF5888");

		assertThat(buscarPorNumero.getNumero()).isEqualTo("FFF5888");
		assertThat(buscarPorNumero.getStatus()).isEqualTo("OK");
	}

}
