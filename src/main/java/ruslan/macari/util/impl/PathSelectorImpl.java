package ruslan.macari.util.impl;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ruslan.macari.util.PathSelector;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

@Component
public class PathSelectorImpl implements PathSelector{
    
    private Runnable actionOk;
    private Runnable actionError;
    private String pathIfOk;
    private String pathIfError;
    private Errors errors;
    
    @Override
    public PathSelector setActionOk(Runnable actionOk) {
        this.actionOk = actionOk;
        return this;
    }
    
    @Override
    public PathSelector setActionError(Runnable actionError) {
        this.actionError = actionError;
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
            actionOk.run();
            return pathIfOk;
        } catch (DuplicateFieldsException ex) {
            errors.rejectValue(ex.getField(), ex.getErrorCode());
            runActionError();
            return pathIfError;
        }
    }

    private void runActionError() {
        if (actionError == null) {
            return;
        }
        actionError.run();
        actionError = null;
    }

}