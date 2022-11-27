package homefinance.common.exception.exceptionshandle;

import java.util.Objects;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    model.addAttribute("errorMessage", request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
    Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    model.addAttribute("statusCode", statusCode);
    return isStatusNotFound(statusCode) ? "resource-not-found" : "exception";
  }

  private boolean isStatusNotFound(Integer statusCode) {
    return Objects.nonNull(statusCode)
        && HttpStatus.NOT_FOUND.value() == statusCode;
  }

  // TODO: 024, 24-Nov-22 ruslan.macari: rewrite
//  @Override
  public String getErrorPath() {
    return "/error";
  }
}
