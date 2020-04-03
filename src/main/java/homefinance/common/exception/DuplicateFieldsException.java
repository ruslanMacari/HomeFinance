package homefinance.common.exception;

import java.util.Map;
import lombok.Generated;

public class DuplicateFieldsException extends RuntimeException {

  private String field;
  private String errorCode;

  public DuplicateFieldsException(Map.Entry<String, String> duplicatedFieldsEntry) {
    this.field = duplicatedFieldsEntry.getKey();
    this.errorCode = duplicatedFieldsEntry.getValue();
  }

  @Generated
  public String getField() {
    return field;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
