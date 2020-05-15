package homefinance.common.exception;

import java.text.MessageFormat;
import java.util.Map;
import lombok.Generated;

public class DuplicateFieldsException extends RuntimeException {

  private String field;
  private String errorCode;

  public DuplicateFieldsException(Map.Entry<String, String> entry) {
    super(MessageFormat
        .format("Field: {0} is duplicated. ErrorCode: {1}", entry.getKey(), entry.getValue()));
    this.field = entry.getKey();
    this.errorCode = entry.getValue();
  }

  public DuplicateFieldsException(String field, String errorCode) {
    super(MessageFormat.format("Field: {0} is duplicated. ErrorCode: {1}", field, errorCode));
    this.field = field;
    this.errorCode = errorCode;
  }

  @Generated
  public String getField() {
    return this.field;
  }

  public String getErrorCode() {
    return this.errorCode;
  }
}
