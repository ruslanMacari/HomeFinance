package ruslan.macari.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import ruslan.macari.web.exceptions.PageNotFoundException;

@ControllerAdvice
public class GlobalAdvice {
    
    @ExceptionHandler(Throwable.class)
    public String exception() {
        return "excetion";
    }
    
    @ExceptionHandler({NoHandlerFoundException.class, PageNotFoundException.class})
    public String noHandlerFound() {
        return "resource-not-found";
    }
    
}
