package ruslan.macari.util;

import org.springframework.validation.Errors;

public interface PathSelector {
    
    PathSelector setAction(Runnable action);
    PathSelector setPaths(String pathIfOk, String pathIfError);
    PathSelector setErrors(Errors errors);
    String getPath();
    
}
