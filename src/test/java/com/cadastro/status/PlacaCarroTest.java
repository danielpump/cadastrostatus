package com.cadastro.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cadastro.status.controles.PlacaCarroControle;
import com.cadastro.status.modelo.PlacaCarro;

@RunWith(SpringRunner.class)
public class PlacaCarroTest extends ApplicationTest {

	@Autowired
	private PlacaCarroControle controle;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testeDeConsultaPorPlacaDeVeículoComStatusOK() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?numero=FFF5888")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5888\",\"status\":\"OK\"}");

	}

	@Test
	public void testeDeConsultaPorPlacaDeVeículoComStatusBloqueado() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?numero=FFF5889")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5889\",\"status\":\"BLOQUEADO\"}");
	}

	@Test
	public void testeDeConsultaPorPlacaDeVeículoComStatusApreendido() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?numero=FFF5890")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5890\",\"status\":\"APREENDIDO\"}");
	}

	@Test
	public void testeDeConsultaPorPlacaDeVeículoInexistente() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?numero=FFF5885"))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5888\",\"status\":\"OK\"}");
	}

	@Test
	public void testeDeConsultaPorStatusComOK() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?status=OK")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"quantidade\":1,\"status\":\"OK\"}");
	}

	@Test
	public void testeDeConsultaPorStatusComBloqueado() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?status=BLOQUEADO")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"quantidade\":2,\"status\":\"BLOQUEADO\"}");
	}

	@Test
	public void testeDeConsultaPorStatusComApreendido() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?status=APREENDIDO")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"quantidade\":1,\"status\":\"APREENDIDO\"}");
	}

}
