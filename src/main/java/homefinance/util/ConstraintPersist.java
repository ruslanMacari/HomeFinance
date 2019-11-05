package homefinance.util;

import homefinance.web.exceptions.DuplicateFieldsException;
import java.util.Map;
import java.util.function.Supplier;
import homefinance.domain.ConstraintEntity;

public interface ConstraintPersist<T extends ConstraintEntity>{
    T add(Supplier<T> supplier, Map<String, String> constraintsMap) throws DuplicateFieldsException;
    void update(Supplier<T> supplier, Map<String, String> constraintsMap) throws DuplicateFieldsException;
}
