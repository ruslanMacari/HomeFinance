package homefinance.util;

import org.springframework.validation.Errors;

// TODO: 25.03.2020 RMACARI: Fix thread issue!!!
public interface PathSelector {

  PathSelector setActionOk(Runnable actionOk);

  PathSelector setActionError(Runnable actionError);

  PathSelector setPaths(String pathIfOk, String pathIfError);

  PathSelector setErrors(Errors errors);

  String getPath();

}
