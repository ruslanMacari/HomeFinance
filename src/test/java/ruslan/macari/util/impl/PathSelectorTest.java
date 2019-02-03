package ruslan.macari.util.impl;

import org.springframework.validation.Errors;
import ruslan.macari.util.Action;
import ruslan.macari.util.PathSelector;

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