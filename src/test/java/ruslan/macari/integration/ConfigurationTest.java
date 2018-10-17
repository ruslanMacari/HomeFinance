package ruslan.macari.integration;

import javax.persistence.EntityManager;
import static org.junit.Assert.*;
import ruslan.macari.config.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebConfig.class})
@WebAppConfiguration
public class ConfigurationTest {

    @Autowired
    private EntityManager entityManager;
    
    @Test
    public void testPersistenceConfiguration() {
        assertNotNull(entityManager);
    }
    
}
