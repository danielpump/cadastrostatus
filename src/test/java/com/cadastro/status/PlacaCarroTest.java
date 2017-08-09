package com.cadastro.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cadastro.status.controles.PlacaCarroControle;

@RunWith(SpringRunner.class)
@Profile("teste")
public class PlacaCarroTest extends ApplicationTest {

	@Autowired
	private PlacaCarroControle controle;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testeDeConsultaPorPlacaDeVeiculoComStatusOK() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?numero=FFF5888")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5888\",\"status\":\"OK\"}");

	}

	@Test
	public void testeDeConsultaPorPlacaDeVeiculoComStatusBloqueado() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?numero=FFF5889")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5889\",\"status\":\"BLOQUEADO\"}");
	}

	@Test
	public void testeDeConsultaPorPlacaDeVeiculoComStatusApreendido() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?numero=FFF5890")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5890\",\"status\":\"APREENDIDO\"}");
	}

	@Test
	public void testeDeConsultaPorPlacaDeVeiculoInexistente() throws Exception {
		String jsonResposta = this.mockMvc.perform(get("/placa/consultar?numero=FFF5885"))
				.andExpect(status().isBadRequest())
				// .andExpect(status().reason(containsString("")))
				.andReturn().getResponse().getContentAsString();
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

	@Test
	public void testeDeCadastroComStatusOK() throws Exception {
		String jsonResposta = this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF5885\",\"status\":\"OK\"}"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5885\",\"status\":\"OK\"}");
	}

	@Test
	public void testeDeAtualizacaoComStatusBloqueado() throws Exception {
		this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF5885\",\"status\":\"OK\"}"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		String jsonResposta = this.mockMvc
				.perform(post("/placa/atualizar?numero=FFF5885").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"status\":\"BLOQUEADO\"}"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5885\",\"status\":\"BLOQUEADO\"}");
	}
	
	@Test
	public void testeDeExclusaoDeVeiculo() throws Exception {
		this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF5885\",\"status\":\"OK\"}"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		String jsonResposta = this.mockMvc
				.perform(delete("/placa/excluir?numero=FFF5885"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5885\",\"status\":\"OK\"}");
	}
}
