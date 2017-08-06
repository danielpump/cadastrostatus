/**
 * 
 */
package com.cadastro.status.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

/**
 * Representa os dados do estado de um veículo
 * 
 * 
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Entity
public class PlacaCarro {
	
	/**
	 * Placa com estado valido
	 */
	public static final String 	STATUS_OK = "OK";
	
	/**
	 * Placa em estado de roubada
	 */
	public static final String 	STATUS_ROUBADO = "ROUBADO";
	
	/**
	 * Placa em estado de apreendia
	 */
	public static final String 	STATUS_APREENDIDO = "APREENDIDO";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	@Getter
	private Long id;
		
	@Column(length = 7)
	@Getter
	private String numero;
	
	@Column(length = 10)
	@Getter
	private String status;
	
	public void dadosUpperCase() {
		this.numero = this.numero.toUpperCase();
		this.status = this.status.toUpperCase();
	}
	
	public void atualizar(PlacaCarro placaCarro) {
		if(!StringUtils.isEmpty(placaCarro.getStatus())) {
			this.status = placaCarro.getStatus();
		}
	}
	
}
