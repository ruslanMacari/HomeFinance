package homefinance.common;

import homefinance.common.exception.DuplicateFieldsException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandleDuplicationException {

  /**
   * url in case of {@link DuplicateFieldsException}
   */
  String urlOnException();
}
