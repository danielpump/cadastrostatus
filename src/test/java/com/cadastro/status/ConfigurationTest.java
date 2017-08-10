package com.cadastro.status;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configurações para os testes integrados
 * 
 * @author Daniel Ferraz
 * @since 9 de ago de 2017
 *
 */
@Configuration
@Profile("teste")
public class ConfigurationTest {

	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
		return hemf.getSessionFactory();
	}

}
