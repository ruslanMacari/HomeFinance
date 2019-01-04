package ruslan.macari.util;

import org.springframework.validation.Errors;

public interface PathSelector {
    
    PathSelector setActionOk(Action actionOk);
    PathSelector setActionError(Action actionError);
    PathSelector setPaths(String pathIfOk, String pathIfError);
    PathSelector setErrors(Errors errors);
    String getPath();
    
}
