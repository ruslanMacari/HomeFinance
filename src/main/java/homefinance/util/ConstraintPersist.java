package homefinance.util;

import homefinance.entity.ConstraintEntity;
import homefinance.web.exceptions.DuplicateFieldsException;
import java.util.Map;
import java.util.function.Supplier;

public interface ConstraintPersist {

  ConstraintEntity add(Supplier<? extends ConstraintEntity> supplier, Map<String, String> constraintsMap) throws DuplicateFieldsException;

  void update(Supplier<? extends ConstraintEntity> supplier, Map<String, String> constraintsMap)
      throws DuplicateFieldsException;
}
