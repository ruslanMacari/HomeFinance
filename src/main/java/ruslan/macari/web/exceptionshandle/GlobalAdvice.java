package ruslan.macari.web.exceptionshandle;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import ruslan.macari.web.exceptions.PageNotFoundException;

@ControllerAdvice
public class GlobalAdvice {
    
    private final Logger logger = LoggerFactory.getLogger(GlobalAdvice.class.getName());
    
    @ExceptionHandler(Throwable.class)
    public String exception(Exception ex, Model model) {
        String error = Jsoup.parse(ex.getMessage()).text();
        logger.error(error, ex);
        model.addAttribute("errorMessage", error);
        return "exception";
    }
    
    @ExceptionHandler({NoHandlerFoundException.class, PageNotFoundException.class})
    public String noHandlerFound(Exception e) {
        logger.error(e.getMessage(), e);
        return "resource-not-found";
    }
    
}
