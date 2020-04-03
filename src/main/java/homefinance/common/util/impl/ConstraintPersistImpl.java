package homefinance.common.util.impl;

import homefinance.common.entity.ConstraintEntity;
import homefinance.common.util.ConstraintPersist;
import homefinance.common.exception.DuplicateFieldsException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ConstraintPersistImpl implements ConstraintPersist {

  private static final Logger logger = LoggerFactory.getLogger(ConstraintPersistImpl.class);

  @Override
  public void update(Supplier<? extends ConstraintEntity> supplier, Map<String, String> constraintsMap)
      throws DuplicateFieldsException {
    this.add(supplier, constraintsMap);
  }

  @Override
  public ConstraintEntity add(Supplier<? extends ConstraintEntity> supplier,
      Map<String, String> constraintsMap) throws DuplicateFieldsException {
    try {
      return supplier.get();
    } catch (DataIntegrityViolationException exception) {
      logger.error(exception.getMessage(), exception);
      String rootMsg = this.getRootCause(exception).getMessage();
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
