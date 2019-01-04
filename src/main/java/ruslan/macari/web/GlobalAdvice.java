package ruslan.macari.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import ruslan.macari.web.exceptions.PageNotFoundException;

@ControllerAdvice
public class GlobalAdvice {
    
    private static final Logger LOGGER = Logger.getLogger(GlobalAdvice.class.getName());
    
    @ExceptionHandler({Throwable.class, Exception.class})
    public String exception(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
        return "exception";
    }
    
    @ExceptionHandler({NoHandlerFoundException.class, PageNotFoundException.class})
    public String noHandlerFound(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
        return "resource-not-found";
    }
    
}
