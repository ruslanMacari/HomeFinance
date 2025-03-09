package homefinance.common.util.impl;

import homefinance.common.entity.ConstraintEntity;
import homefinance.common.util.ConstraintPersist;
import homefinance.common.exception.DuplicateFieldsException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConstraintPersistImpl implements ConstraintPersist {

  @Override
  public void update(Supplier<? extends ConstraintEntity> supplier, Map<String, String> constraintsMap)
      throws DuplicateFieldsException {
    add(supplier, constraintsMap);
  }

  @Override
  public ConstraintEntity add(Supplier<? extends ConstraintEntity> supplier,
      Map<String, String> constraintsMap) throws DuplicateFieldsException {
    try {
      return supplier.get();
    } catch (DataIntegrityViolationException exception) {
      log.error(exception.getMessage(), exception);
      String rootMsg = getRootCause(exception).getMessage();
      if (rootMsg != null) {
        String lowerCaseMsg = rootMsg.toLowerCase();
        Optional<Map.Entry<String, String>> entry = constraintsMap.entrySet().stream()
            .filter(it -> lowerCaseMsg.contains(it.getValue()))
            .findAny();
        if (entry.isPresent()) {
          throw new DuplicateFieldsException(entry.get());
        }
      }
      //strange situation
      throw exception;
    }
  }

  private Throwable getRootCause(Throwable t) {
    Throwable result = t;
    Throwable cause;
    while (null != (cause = result.getCause())
        && (result != cause)) {
      result = cause;
    }
    return result;
  }

}
