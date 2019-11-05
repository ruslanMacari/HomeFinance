package homefinance.util.impl;

import homefinance.service.impl.UserServiceImpl;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import homefinance.domain.ConstraintEntity;
import homefinance.web.exceptions.DuplicateFieldsException;
import homefinance.util.ConstraintPersist;

@Component
public class ConstraintPersistImpl implements ConstraintPersist<ConstraintEntity>{

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());
    
    @Override
    public ConstraintEntity add(Supplier<ConstraintEntity> supplier, Map<String, String> constraintsMap) throws DuplicateFieldsException {
        try {
            return supplier.get();
        } catch (DataIntegrityViolationException exception) {
            logger.error(exception.getMessage(), exception);
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
    
    @Override
    public void update(Supplier<ConstraintEntity> supplier, Map<String, String> constraintsMap) throws DuplicateFieldsException {
        add(supplier, constraintsMap);
    }

}
