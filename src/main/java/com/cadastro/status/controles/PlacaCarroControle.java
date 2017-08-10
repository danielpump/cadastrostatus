/**
 * 
 */
package com.cadastro.status.controles;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.status.excecoes.NegocioException;
import com.cadastro.status.modelo.PlacaCarro;
import com.cadastro.status.servicos.PlacaCarroServico;

/**
 * Controlador das requisições dos serviços que a aplicação implementa
 * 
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@RestController
public class PlacaCarroControle {

	/**
	 * Serviço da entidade da aplicação
	 */
	@Autowired
	private PlacaCarroServico servico;

	/**
	 * Realiza o cadastro de uma placa no sistema utilizando um requisição POST com o JSON<br>
	 * {<br>
	 * 		"numero":"Placa do veículo",<br>
	 * 		"status":"Status do veículo"<br>
	 * }
	 * 
	 * @param placa Entidade da aplicação convertida do JSON recebido
	 * @return Retorna os dados da entidade que foi cadastrada
	 * @exception NegocioException Retorna um HTTP 400 em caso de falha na validação dos dados
	 */
	@RequestMapping(path = "/placa/cadastrar", method = RequestMethod.POST)
	public PlacaCarro cadastrar(@RequestBody PlacaCarro placa) {
		return servico.gravar(placa);
	}

	/**
	 * Realiza a atualização de uma placa no sistema utilizando um requisição POST tendo o parametro {@code numero} como placa do veículo na URL e com o JSON<br>
	 * {<br> 		
	 * 		"status":"Status do veículo"<br>
	 * }
	 * 
	 * @param placa Entidade da aplicação convertida do JSON recebido
	 * @param numero Placa do veículo recebido
	 * @return Retorna os dados da entidade que foi atualizada
	 * @exception NegocioException Retorna um HTTP 400 em caso de falha na validação dos dados
	 */
	@RequestMapping(path = "/placa/atualizar", method = RequestMethod.POST, params = "numero")
	public PlacaCarro atualizar(@RequestParam String numero, @RequestBody PlacaCarro placa) {
		return servico.atualizar(numero, placa);
	}

	/**
	 * Realiza a consulta de uma placa no sistema utilizando uma requisição GET tendo o parametro {@code numero} como placa do veículo na URL<br>
	 * Formato do JSON de resposta<br>
	 * {<br>
	 * 		"numero":"Placa do veículo",<br>
	 * 		"status":"Status do veículo"<br>
	 * }
	 * 
	 * @param numero Placa do veículo que será consultado
	 * @return Retorna os dados da entidade que foi consultada
	 * @exception NegocioException Retorna um HTTP 400 em caso de falha na validação dos dados da placa
	 */
	@RequestMapping(path = "/placa/consultar", params = "numero", method = RequestMethod.GET)
	public PlacaCarro buscarPorNumero(@RequestParam String numero) {
		return servico.buscarPorNumero(numero);
	}

	/**
	 * Realiza a consulta de um status no sistema utilizando uma requisição GET tendo o parametro {@code status} como status a ser contabilizado na URL<br>
	 * Formato do JSON de resposta<br>
	 * 
	 * {<br>		
	 * 		"status":"Status do veículo",<br>
	 * 		"quantidade":"Quantidade de placas no status"<br>
	 * }
	 * 
	 * @param status Status de placa a ser consultada
	 * @return Retorna o JSON com os dados de quantidade de placas cdastradas
	 * @exception NegocioException Retorna um HTTP 400 em caso de falha na validação dos dados
	 */
	@RequestMapping(path = "/placa/consultar", params = "status", method = RequestMethod.GET)
	public Map<String, Object> buscarPorStatus(@RequestParam String status) {
		status = status.toUpperCase();
		HashMap<String, Object> mapa = new HashMap<>();
		mapa.put("status", status);
		mapa.put("quantidade", servico.quantidadePorStatus(status));
		return mapa;
	}

	/**
	 * Realiza a exclusão de uma placa no sistema utilizando uma requisição DELETE tendo o parametro {@code numero} como placa do veículo na URL<br>
	 * Formato do JSON de resposta<br>
	 * {<br>
	 * 		"numero":"Placa do veículo",<br>
	 * 		"status":"Status do veículo"<br>
	 * }
	 * 
	 * @param numero Placa do veículo que será excluído
	 * @return Retorna os dados da entidade que foi excluída
	 * @exception NegocioException Retorna um HTTP 400 em caso de falha na validação dos dados da placa
	 */
	@RequestMapping(path = "/placa/excluir", method = RequestMethod.DELETE, params = "numero")
	public PlacaCarro excluir(@RequestParam String numero) {
		return servico.excluir(numero);
	}

}
