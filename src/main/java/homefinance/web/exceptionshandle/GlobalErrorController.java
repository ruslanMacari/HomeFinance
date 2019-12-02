package homefinance.web.exceptionshandle;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
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
    return "exception";

  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
