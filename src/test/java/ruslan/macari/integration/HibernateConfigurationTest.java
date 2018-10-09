package ruslan.macari.integration;

import javax.persistence.EntityManager;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(locations = "/spring/appServlet/servlet-context.xml")
public class HibernateConfigurationTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
        private EntityManager entityManager;

	@Test
	public void testHibernateConfiguration() {
		// Spring IOC container instantiated and prepared sessionFactory
		assertNotNull (entityManager); 
	}

}
