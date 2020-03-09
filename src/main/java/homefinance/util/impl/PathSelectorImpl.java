package homefinance.util.impl;

import homefinance.util.Action;
import homefinance.util.PathSelector;
import homefinance.web.exceptions.DuplicateFieldsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PathSelectorImpl implements PathSelector {

  private Action actionOk;
  private Action actionError;
  private String pathIfOk;
  private String pathIfError;
  private Errors errors;

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
  public PathSelectorImpl setErrors(Errors errors) {
    this.errors = errors;
    return this;
  }

  @Override
  public String getPath() {
    try {
        this.actionOk.execute();
      return this.pathIfOk;
    } catch (DuplicateFieldsException ex) {
        this.errors.rejectValue(ex.getField(), ex.getErrorCode());
        this.runActionError();
      return this.pathIfError;
    }
  }

  private void runActionError() {
    if (this.actionError == null) {
      return;
    }
      this.actionError.execute();
      this.actionError = null;
  }

}