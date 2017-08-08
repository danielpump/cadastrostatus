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

import com.cadastro.status.modelo.PlacaCarro;
import com.cadastro.status.servicos.PlacaCarroServico;

/**
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@RestController
public class PlacaCarroControle {

	@Autowired
	private PlacaCarroServico servico;

	@RequestMapping(path = "/placa/cadastrar", method = RequestMethod.POST)
	public PlacaCarro cadastrar(@RequestBody PlacaCarro placa) {

		return servico.gravar(placa);
	}

	@RequestMapping(path = "/placa/atualizar", method = RequestMethod.POST, params = "numero")
	public PlacaCarro atualizar(@RequestParam String numero, @RequestBody PlacaCarro placa) {

		return servico.atualizar(numero, placa);
	}

	@RequestMapping(path = "/placa/consultar", params = "numero", method = RequestMethod.GET)
	public PlacaCarro buscarPorNumero(@RequestParam String numero) {

		return servico.buscarPorNumero(numero);
	}

	@RequestMapping(path = "/placa/consultar", params = "status", method = RequestMethod.GET)
	public Map<String, Object> buscarPorStatus(@RequestParam String status) {

		status = status.toUpperCase();

		HashMap<String, Object> mapa = new HashMap<>();
		mapa.put("status", status);
		mapa.put("quantidade", servico.quantidadePorStatus(status));

		return mapa;
	}

	@RequestMapping(path = "/placa/excluir", method = RequestMethod.DELETE, params = "numero")
	public PlacaCarro excluir(@RequestParam String numero) {

		return servico.excluir(numero);
	}

}
