package ruslan.macari.util.impl;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import ruslan.macari.domain.ConstraintEntity;
import ruslan.macari.service.impl.UserServiceImpl;
import ruslan.macari.util.SafePersist;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

@Component
public class SafePersistImpl implements SafePersist<ConstraintEntity>{

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    
    @Override
    public ConstraintEntity add(Supplier<ConstraintEntity> supplier, Map<String, String> constraintsMap) throws DuplicateFieldsException {
        try {
            return supplier.get();
        } catch (DataIntegrityViolationException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage());
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
        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
    
    @Override
    public void update(Supplier<ConstraintEntity> supplier, Map<String, String> constraintsMap) throws DuplicateFieldsException {
        add(supplier, constraintsMap);
    }

}
