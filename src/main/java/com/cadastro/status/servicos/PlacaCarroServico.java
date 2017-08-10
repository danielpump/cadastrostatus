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
 * Servi�os para a entidade {@link PlacaCarro}}
 * 
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Service
public class PlacaCarroServico {

	/**
	 * Reposit�rio de acesso a dados da entidade
	 */
	@Autowired
	private PlacaCarroRepositorio repositorio;

	/**
	 * Salva a entidade {@link PlacaCarro} no banco de dados identificando se e um novo registro ou uma atualiza��o.<br>
	 * Realiza a valida��o dos dados da entidade antes de persistir ela no banco.
	 * 
	 * @param placaCarro Ve�culo que ser� salvo no banco de dados
	 * @return Informa��es do ve�culo que foi salvo
	 * @exception NegocioException Lan�a exce��o de negocio caso o ve�culo n�o passe em alguma das valida��es
	 */
	public PlacaCarro gravar(PlacaCarro placaCarro) {

		if (StringUtils.isEmpty(placaCarro.getNumero())) {
			throw new NegocioException("Placa deve ser preenchida");
		}
		
		if (StringUtils.isEmpty(placaCarro.getStatus())) {
			throw new NegocioException("Status deve ser preenchido");
		}

		placaCarro.dadosUpperCase();

		if (!placaCarro.getNumero().matches("^[A-Z]{3}\\d{4}$")) {
			throw new NegocioException("Placa fora de formato padronizado");
		}

		if (placaCarro.getId() == null) {
			PlacaCarro buscarPorNumero = repositorio.findByNumero(placaCarro.getNumero());
			if (buscarPorNumero != null) {
				throw new NegocioException("Registro existente na base de dados");
			}
		}

		return repositorio.save(placaCarro);
	}

	/**
	 * Busca um ve�culo pela  placa
	 * 
	 * @param numero Placa a ser consultada
	 * @return Entidade que foi localizada
	 * @exception NegocioException Lan�a exce��o de negocio caso n�o encontre o ve�culo
	 */
	public PlacaCarro buscarPorNumero(String numero) {
		PlacaCarro placaCarro = repositorio.findByNumero(numero.toUpperCase());
		if (placaCarro == null) {
			throw new NegocioException("Registro sem cadastro no banco de dados");
		}
		return placaCarro;
	}

	/**
	 * Carrega a quantidade de itens com um determinado status
	 * 
	 * @param status Status a ser consultado
	 * @return Quantidade localizada com o status
	 */
	public Long quantidadePorStatus(String status) {
		return repositorio.countByStatus(status);
	}

	/**
	 * Atualiza um ve�cul com os novos dados passados
	 * 
	 * @param numero Placa para ser atualizadas
	 * @param novosDados Novos dados que ser�o atualizados
	 * @return Ve�culo com dados atualizados
	 * @exception NegocioException Lan�a exce��o de negocio caso o ve�culo n�o passe em alguma das valida��es
	 */
	public PlacaCarro atualizar(String numero, PlacaCarro novosDados) {
		PlacaCarro placaCarro = buscarPorNumero(numero);
		placaCarro.atualizar(novosDados);
		return gravar(placaCarro);
	}

	/**
	 * Remove um ve�culo da aplica��o
	 * 
	 * @param numero Placa a ser removida
	 * @return Entidade que foi removida
	 * @exception NegocioException Lan�a exce��o de negocio caso n�o encontre o ve�culo
	 */
	public PlacaCarro excluir(String numero) {
		PlacaCarro placaCarro = buscarPorNumero(numero);
		repositorio.delete(placaCarro);
		return placaCarro;
	}

}
