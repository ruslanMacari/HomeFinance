package homefinance.common.exception.exceptionshandle;

import homefinance.common.RequestBuffer;
import homefinance.common.exception.DuplicateFieldsException;
import homefinance.common.exception.PageNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

  private RequestBuffer requestBuffer;

  @Autowired
  public GlobalExceptionHandler(RequestBuffer requestBuffer) {
    this.requestBuffer = requestBuffer;
  }

  @ExceptionHandler(Throwable.class)
  public String exception(HttpServletRequest request, Exception ex, Model model) {
    String error = Jsoup.parse(ex.getMessage()).text();
    logger.error(error, ex);
    model.addAttribute("errorMessage", error);
    model.addAttribute("statusCode", this.getStatus(request));
    return "exception";
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.valueOf(statusCode);
  }

  @ExceptionHandler({NoHandlerFoundException.class, PageNotFoundException.class})
  public String noHandlerFound(Exception e) {
    logger.error(e.getMessage(), e);
    return "resource-not-found";
  }

  @ExceptionHandler(DuplicateFieldsException.class)
  public ModelAndView duplicateFieldException(DuplicateFieldsException e) {
    logger.error(e.getMessage(), e);
    this.requestBuffer.getErrors().rejectValue(e.getField(), e.getErrorCode());
    return new ModelAndView(this.requestBuffer.getViewName(), this.requestBuffer.getErrors().getModel());
  }

}
