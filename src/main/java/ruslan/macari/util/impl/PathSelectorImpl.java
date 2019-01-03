package ruslan.macari.util.impl;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ruslan.macari.util.PathSelector;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

@Component
public class PathSelectorImpl implements PathSelector{
    
    private Runnable action;
    private String pathIfOk;
    private String pathIfError;
    private Errors errors;
    
    @Override
    public PathSelector setAction(Runnable action) {
        this.action = action;
        return this;
    }

    @Override
    public PathSelector setPaths(String pathIfOk, String pathIfError) {
        this.pathIfOk = pathIfOk;
        this.pathIfError = pathIfError;
        return this;
    }
    
    @Override
    public PathSelectorImpl setErrors(Errors errors) {
        this.errors = errors;
        return this;
    }
    
    @Override
    public String getPath() {
        try {
            action.run();
            return pathIfOk;
        } catch (DuplicateFieldsException ex) {
            errors.rejectValue(ex.getField(), ex.getErrorCode());
            return pathIfError;
        }
    }

    
    
}
