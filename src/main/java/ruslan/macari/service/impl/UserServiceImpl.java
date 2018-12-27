package ruslan.macari.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruslan.macari.security.User;
import ruslan.macari.service.repository.UserRepository;
import ruslan.macari.service.UserService;
import ruslan.macari.util.ValidationUtil;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Value("${db.username}")
    private String rootname;
    
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User add(User user) throws DuplicateFieldsException{
        try {
            User savedUser = userRepository.saveAndFlush(user);
            return savedUser;
        } catch (DataIntegrityViolationException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage());
            String rootMsg = ValidationUtil.getRootCause(exception).getMessage();
            if (rootMsg != null) {
                String lowerCaseMsg = rootMsg.toLowerCase();
                Optional<Map.Entry<String, String>> entry = User.CONSTRAINS_I18N_MAP.entrySet().stream()
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
    public void update(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public List<User> listLimit(Integer limit) {
        return userRepository.listLimit(new PageRequest(0, limit));
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public User getByNameAndPassword(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

    @Override
    public List<User> getSimpleUsers() {
        return userRepository.getSimpleUsers();
    }

    @Override
    public List<User> usersExceptRoot() {
        return userRepository.usersExceptRoot(rootname);
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findOne(id);
    }

}
