package homefinance.common.exception.exceptionshandle;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import homefinance.common.beans.JsoupAdapter;
import homefinance.common.exception.PageNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());
  private final JsoupAdapter jsoup;

  public GlobalExceptionHandler(JsoupAdapter jsoup) {
    this.jsoup = jsoup;
  }

  @ExceptionHandler(Throwable.class)
  public String exception(HttpServletRequest request, Exception ex, Model model) {
    String error = jsoup.parse(ex.getMessage()).text();
    logger.error(error, ex);
    model.addAttribute("errorMessage", error);
    model.addAttribute("statusCode", getStatus(request));
    return "exception";
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
    return (code == null) ? INTERNAL_SERVER_ERROR : HttpStatus.valueOf(code);
  }

  @ExceptionHandler({NoHandlerFoundException.class, PageNotFoundException.class})
  public String noHandlerFound(Exception e) {
    logger.error(e.getMessage(), e);
    return "resource-not-found";
  }

}
