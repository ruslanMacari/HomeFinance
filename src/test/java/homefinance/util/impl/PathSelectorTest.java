package homefinance.util.impl;

import org.springframework.validation.Errors;
import homefinance.util.Action;
import homefinance.util.PathSelector;

public class PathSelectorTest implements PathSelector {

    public Action actionOk;
    public Action actionError;
    public String pathIfOk, pathIfError;
    public Errors errors;

    @Override
    public PathSelector setActionOk(Action actionOk) {
        this.actionOk = actionOk;
        return this;
    }

    @Override
    public PathSelector setActionError(Action actionError) {
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
    public PathSelector setErrors(Errors errors) {
        this.errors = errors;
        return this;
    }

    @Override
    public String getPath() {
        try {
            actionOk.execute();
            return pathIfOk;
        } catch (Exception e) {
            actionError.execute();
            return pathIfError;
        }
    }

}
