package ruslan.macari.integration;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import static org.junit.Assert.*;
import ruslan.macari.config.AppConfig;
import ruslan.macari.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import ruslan.macari.config.security.SecurityConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SecurityConfig.class})
@WebAppConfiguration
public class ConfigurationTest {

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private WebApplicationContext wac;
    
    @Test
    public void testPersistenceConfiguration() {
        assertNotNull(entityManager);
    }
    
    @Test
    public void testLoginController() {
        ServletContext servletContext = wac.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(wac.getBean("loginController"));
    }
    
}
