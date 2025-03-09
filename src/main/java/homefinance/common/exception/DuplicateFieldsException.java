package homefinance.common.exception;

import java.text.MessageFormat;
import java.util.Map;
import lombok.Generated;
import lombok.Getter;

public class DuplicateFieldsException extends RuntimeException {

  private final String field;
  @Getter
  private final String errorCode;

  public DuplicateFieldsException(Map.Entry<String, String> entry) {
    super(MessageFormat.format("Field: {0} is duplicated. ErrorCode: {1}", entry.getKey(), entry.getValue()));
    field = entry.getKey();
    errorCode = entry.getValue();
  }

  public DuplicateFieldsException(String field, String errorCode) {
    super(MessageFormat.format("Field: {0} is duplicated. ErrorCode: {1}", field, errorCode));
    this.field = field;
    this.errorCode = errorCode;
  }

  @Generated
  public String getField() {
    return field;
  }

}
