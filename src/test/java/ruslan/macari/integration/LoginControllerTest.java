package ruslan.macari.integration;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import static org.junit.Assert.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.Before;
import ruslan.macari.config.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ruslan.macari.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebConfig.class})
@WebAppConfiguration
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext wac;
    
    @Autowired 
    MockHttpSession session; 
    
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testLoginController() {
        ServletContext servletContext = wac.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(wac.getBean("loginController"));
    }
    
    @Test
    public void testMainPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andDo(print()).andExpect(MockMvcResultMatchers.view().name("redirect:/login"));
        session.setAttribute("user", new User());
        mockMvc.perform(MockMvcRequestBuilders.get("/").session(session)).andDo(print()).andExpect(MockMvcResultMatchers.view().name("redirect:/home"));
    }

}
