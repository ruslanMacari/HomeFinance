package homefinance.web.exceptionshandle;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
    model.addAttribute("errorMessage", exception);
    model.addAttribute("statusCode", statusCode);
    if (Objects.nonNull(statusCode)
      && HttpStatus.NOT_FOUND.value() == statusCode) {
      return "resource-not-found";
    }
    return "exception";

  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
