package ruslan.macari.util.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ruslan.macari.domain.ConstraintEntity;
import ruslan.macari.service.impl.UserServiceImpl;
import ruslan.macari.util.SafePersist;
import ruslan.macari.util.ValidationUtil;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

@Component
public class SafePersistImpl implements SafePersist<ConstraintEntity>{

    private JpaRepository<ConstraintEntity, Serializable> repository;
    
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    
    @Override
    public void setRepository(JpaRepository<ConstraintEntity, Serializable> repository) {
        this.repository = repository;
    }
    
    @Override
    public ConstraintEntity add(ConstraintEntity entity) throws DuplicateFieldsException {
        try {
            ConstraintEntity savedUser = repository.saveAndFlush(entity);
            return savedUser;
        } catch (DataIntegrityViolationException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage());
            String rootMsg = ValidationUtil.getRootCause(exception).getMessage();
            if (rootMsg != null) {
                String lowerCaseMsg = rootMsg.toLowerCase();
                Optional<Map.Entry<String, String>> entry = entity.getConstraintsMap().entrySet().stream()
                        .filter(it -> lowerCaseMsg.contains(it.getValue()))
                        .findAny();
                if (entry.isPresent()) {
                    throw new DuplicateFieldsException(entry.get());
                }
            }
            // strange situation
            throw exception;
        }
    }

    @Override
    public void update(ConstraintEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
