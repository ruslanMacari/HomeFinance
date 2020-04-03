package homefinance.common.exception.exceptionshandle;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to intercept all exceptions raised on rendering view
 */
public class JSPExceptionFilter implements Filter {

  private final Logger logger = LoggerFactory.getLogger(JSPExceptionFilter.class);

  @Override
  public void destroy() {
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain)
      throws IOException, ServletException {
    try {
      chain.doFilter(request, response);
    } catch (Exception ex) {
      String error = Jsoup.parse(ex.getMessage()).text();
      logger.error(error, ex);
      request.setAttribute("errorMessage", error);
      request.getRequestDispatcher("/WEB-INF/views/exception.jsp")
          .forward(request, response);
    }

  }

}
