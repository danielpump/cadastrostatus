/**
 * 
 */
package com.cadastro.status.servicos;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadastro.status.excecoes.NegocioException;
import com.cadastro.status.modelo.PlacaCarro;
import com.cadastro.status.repositorios.PlacaCarroRepositorio;

/**
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Service
public class PlacaCarroServico {

	@Autowired
	private PlacaCarroRepositorio repositorio;
	
	public PlacaCarro gravar(PlacaCarro placaCarro) {
		
		if(StringUtils.isEmpty(placaCarro.getNumero())) {
			throw new NegocioException("Placa deve ser preenhida");
		}
		
		placaCarro.numeroUpperCase();
		
		if(placaCarro.getNumero().matches("^[A-Z]{3}\\\\d{4}$")) {
			throw new NegocioException("Placa fora de formato");
		}
		
		return repositorio.save(placaCarro);
	}
	
	public PlacaCarro buscarPorNumero(String numero) {
		PlacaCarro placaCarro = repositorio.findByNumero(numero);
		if(placaCarro == null) {
			throw new NegocioException("Registro n�o existe");
		}
		return placaCarro;
	}
	
	public Long quantidadePorStatus(String status) {		
		return repositorio.count();
	}
	
	public PlacaCarro atualizar(String numero, PlacaCarro novosDados) {		
		PlacaCarro placaCarro = buscarPorNumero(numero);
		placaCarro.atualizar(novosDados);
		return gravar(placaCarro);
	}
	
	public PlacaCarro excluir(String numero) {
		PlacaCarro placaCarro = buscarPorNumero(numero);
		repositorio.delete(placaCarro);
		return placaCarro;
	}
	
}
