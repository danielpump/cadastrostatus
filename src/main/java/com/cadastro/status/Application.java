/**
 * 
 */
package com.cadastro.status;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * Classe que inicializa a aplicação
 * 
 * @author Daniel Ferraz
 * @since 4 de ago de 2017
 *
 */
@SpringBootApplication
@ComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
