package ruslan.macari.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import ruslan.macari.web.exceptions.PageNotFoundException;

@ControllerAdvice
public class GlobalAdvice {
    
    private final Logger logger = LoggerFactory.getLogger(GlobalAdvice.class.getName());
    
    @ExceptionHandler({Throwable.class, Exception.class})
    public String exception(Exception e) {
        logger.error(e.getMessage(), e);
        return "exception";
    }
    
    @ExceptionHandler({NoHandlerFoundException.class, PageNotFoundException.class})
    public String noHandlerFound(Exception e) {
        logger.error(e.getMessage(), e);
        return "resource-not-found";
    }
    
}
