package homefinance.web.exceptionshandle;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {

  @RequestMapping("/error")
  public void handleError(HttpServletRequest request) throws Throwable {
    Object error = request.getAttribute("javax.servlet.error.exception");
    if (error != null) {
      throw (Throwable) error;
    }
  }

  @Override
  public String getErrorPath() {
    return null;
  }
}
