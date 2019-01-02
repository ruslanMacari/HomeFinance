package ruslan.macari.util;

import java.util.Map;
import java.util.function.Supplier;
import ruslan.macari.domain.ConstraintEntity;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

public interface ConstraintPersist<T extends ConstraintEntity>{
    T add(Supplier<T> supplier, Map<String, String> constraintsMap) throws DuplicateFieldsException;
    void update(Supplier<T> supplier, Map<String, String> constraintsMap) throws DuplicateFieldsException;
}
