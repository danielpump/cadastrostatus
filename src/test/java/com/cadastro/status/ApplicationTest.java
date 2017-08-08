/**
 * 
 */
package com.cadastro.status;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Daniel Ferraz
 * @since 7 de ago de 2017
 *
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = { Application.class })
@ContextConfiguration(loader = SpringBootContextLoader.class)
@AutoConfigureMockMvc
@ActiveProfiles("teste")
public class ApplicationTest {
	
    @Autowired
    SessionFactory sessionFactory;

	@PostConstruct
	public void init() throws DatabaseUnitException, SQLException {
		IDataSet dataSet = new FlatXmlDataFileLoader().load("databaseTest.xml");

		try (Connection jdbcConnection = SessionFactoryUtils.getDataSource(sessionFactory).getConnection()) {
			IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);			

			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		}
	}

}
