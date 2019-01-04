package ruslan.macari.util;

import org.springframework.validation.Errors;

public interface PathSelector {
    
    PathSelector setActionOk(Runnable actionOk);
    PathSelector setActionError(Runnable actionError);
    PathSelector setPaths(String pathIfOk, String pathIfError);
    PathSelector setErrors(Errors errors);
    String getPath();
    
}
