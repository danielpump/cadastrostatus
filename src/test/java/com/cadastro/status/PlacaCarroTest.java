package com.cadastro.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cadastro.status.excecoes.NegocioException;

@RunWith(SpringRunner.class)
@Profile("teste")
public class PlacaCarroTest extends ApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Rule
	public ExpectedException exception = ExpectedException.none();

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
		String jsonResposta = this.mockMvc
				.perform(get("/placa/consultar?numero=FFF5890").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5890\",\"status\":\"APREENDIDO\"}");
	}

	@Test
	public void testeDeConsultaPorPlacaDeVeiculoInexistente() throws Exception {
		String message = this.mockMvc.perform(get("/placa/consultar?numero=FFF5885")).andExpect(status().isBadRequest())
				.andReturn().getResolvedException().getMessage();
		assertThat(message).isEqualTo("Registro sem cadastro no banco de dados");
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
	public void testeDeCadastroComComPlacaInvalida() throws Exception {
		String mensagem = this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF588\",\"status\":\"OK\"}"))
				.andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

		assertThat(mensagem).isEqualTo("Placa fora de formato padronizado");

	}

	@Test
	public void testeDeCadastroComComPlacaDuplicada() throws Exception {
		String mensagem = this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF5889\",\"status\":\"OK\"}"))
				.andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

		assertThat(mensagem).isEqualTo("Registro existente na base de dados");

	}

	@Test
	public void testeDeCadastroSemCampoPlaca() throws Exception {
		String mensagem = this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"status\":\"OK\"}"))
				.andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

		assertThat(mensagem).isEqualTo("Placa deve ser preenchida");

	}

	@Test
	public void testeDeCadastroSemCampoStatus() throws Exception {
		String mensagem = this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF5885\"}"))
				.andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

		assertThat(mensagem).isEqualTo("Status deve ser preenchido");
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
	public void testeDeAtualizacaoComPlacaInvalida() throws Exception {
		this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF5885\",\"status\":\"OK\"}"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		String mensagem = this.mockMvc
				.perform(post("/placa/atualizar?numero=FFF588").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"status\":\"BLOQUEADO\"}"))
				.andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

		assertThat(mensagem).isEqualTo("Registro sem cadastro no banco de dados");
	}
	
	@Test
	public void testeDeAtualizacaoComGarantiaDeQuePlacaFFF5885NaoEhAtualizadaPorFFF5889() throws Exception {
		this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF5885\",\"status\":\"OK\"}"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		String jsonResposta = this.mockMvc
				.perform(post("/placa/atualizar?numero=FFF5885").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF5889\",\"status\":\"BLOQUEADO\"}"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5885\",\"status\":\"BLOQUEADO\"}");
	}

	@Test
	public void testeDeExclusaoDeVeiculo() throws Exception {
		this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF5885\",\"status\":\"OK\"}"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		String jsonResposta = this.mockMvc.perform(delete("/placa/excluir?numero=FFF5885")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(jsonResposta).isEqualTo("{\"numero\":\"FFF5885\",\"status\":\"OK\"}");
	}
	
	@Test
	public void testeDeExclusaoDeVeiculoComPlacaInexistente() throws Exception {
		this.mockMvc
				.perform(post("/placa/cadastrar").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"numero\":\"FFF5885\",\"status\":\"OK\"}"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		String mensagem = this.mockMvc.perform(delete("/placa/excluir?numero=FFF5886"))
				.andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

		assertThat(mensagem).isEqualTo("Registro sem cadastro no banco de dados");
	}

}
