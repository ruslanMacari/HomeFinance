package ruslan.macari.util;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import ruslan.macari.domain.ConstraintEntity;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

public interface SafePersist<T extends ConstraintEntity>{
    void setRepository(JpaRepository<T, Serializable> repository);
    T add(T entity) throws DuplicateFieldsException;
    void update(T entity);
}
