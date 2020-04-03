package homefinance.common.util.impl;

import homefinance.common.util.PathSelector;
import homefinance.common.exception.DuplicateFieldsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PathSelectorImpl implements PathSelector {

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
      this.actionOk.run();
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
    this.actionError.run();
    this.actionError = null;
  }

}