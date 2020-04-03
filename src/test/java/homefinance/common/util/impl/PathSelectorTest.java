package homefinance.common.util.impl;

import homefinance.common.util.PathSelector;
import org.springframework.validation.Errors;

public class PathSelectorTest implements PathSelector {

  public Runnable actionOk;
  public Runnable actionError;
  public String pathIfOk, pathIfError;
  public Errors errors;

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
  public PathSelector setErrors(Errors errors) {
    this.errors = errors;
    return this;
  }

  @Override
  public String getPath() {
    try {
      actionOk.run();
      return pathIfOk;
    } catch (Exception e) {
      actionError.run();
      return pathIfError;
    }
  }

}
