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
 * Serviços para a entidade {@link PlacaCarro}}
 * 
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Service
public class PlacaCarroServico {

	/**
	 * Repositório de acesso a dados da entidade
	 */
	@Autowired
	private PlacaCarroRepositorio repositorio;

	/**
	 * Salva a entidade {@link PlacaCarro} no banco de dados identificando se e um novo registro ou uma atualização.<br>
	 * Realiza a validação dos dados da entidade antes de persistir ela no banco.
	 * 
	 * @param placaCarro Veículo que será salvo no banco de dados
	 * @return Informações do veículo que foi salvo
	 * @exception NegocioException Lança exceção de negocio caso o veículo não passe em alguma das validações
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
	 * Busca um veículo pela  placa
	 * 
	 * @param numero Placa a ser consultada
	 * @return Entidade que foi localizada
	 * @exception NegocioException Lança exceção de negocio caso não encontre o veículo
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
	 * Atualiza um veícul com os novos dados passados
	 * 
	 * @param numero Placa para ser atualizadas
	 * @param novosDados Novos dados que serão atualizados
	 * @return Veículo com dados atualizados
	 * @exception NegocioException Lança exceção de negocio caso o veículo não passe em alguma das validações
	 */
	public PlacaCarro atualizar(String numero, PlacaCarro novosDados) {
		PlacaCarro placaCarro = buscarPorNumero(numero);
		placaCarro.atualizar(novosDados);
		return gravar(placaCarro);
	}

	/**
	 * Remove um veículo da aplicação
	 * 
	 * @param numero Placa a ser removida
	 * @return Entidade que foi removida
	 * @exception NegocioException Lança exceção de negocio caso não encontre o veículo
	 */
	public PlacaCarro excluir(String numero) {
		PlacaCarro placaCarro = buscarPorNumero(numero);
		repositorio.delete(placaCarro);
		return placaCarro;
	}

}
