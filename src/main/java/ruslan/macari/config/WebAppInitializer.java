package ruslan.macari.config;

import ruslan.macari.config.security.SecurityConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

   @Override
   protected Class<?>[] getRootConfigClasses() {
      return new Class[] {AppConfig.class, SecurityConfig.class};
   }

   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class[] {WebConfig.class};
   }

   @Override
   protected String[] getServletMappings() {
      return new String[] {"/"};
   }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("Title", "Home Finance");
        registerHiddenFieldFilter(servletContext);
    }
    
    private void registerHiddenFieldFilter(ServletContext servletContext) {
        servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*"); 
    }
    
    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }
    
}
