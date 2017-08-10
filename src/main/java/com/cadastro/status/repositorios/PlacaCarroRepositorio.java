/**
 * 
 */
package com.cadastro.status.repositorios;

import org.springframework.data.repository.CrudRepository;

import com.cadastro.status.modelo.PlacaCarro;

/**
 * Implementa o CRUD padrão do Spring.
 * Repositório para acesso aos dados do banco de dados da classe {@link PlacaCarro}
 * 
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
public interface PlacaCarroRepositorio  extends CrudRepository<PlacaCarro, Long> {
	
	/**
	 * Localiza um veículo pela placa
	 * 
	 * @param numero Placa a ser consultada
	 * @return Retorna um objeto {link PlacaCarro} caso encontre o item ou um {@code null} caso ele não seja encontrado
	 */
	PlacaCarro findByNumero(String numero);
		
	/**
	 * Contabiliza os registro de um determinado status na base de dados
	 * 
	 * @param status Status a ser contabilizado
	 * @return Retorna quantidade de registros em determinado status
	 */
	Long countByStatus(String status);

}
