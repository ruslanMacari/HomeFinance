package ruslan.macari.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruslan.macari.security.User;
import ruslan.macari.service.repository.UserRepository;
import ruslan.macari.service.UserService;
import ruslan.macari.util.SafePersist;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Value("${db.username}")
    private String rootname;
    
    private SafePersist safePersist;
    
    @Autowired
    public void setSafePersist(SafePersist safePersist) {
        this.safePersist = safePersist;
    }
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User add(User user) throws DuplicateFieldsException{
        return (User) safePersist.add(() -> userRepository.saveAndFlush(user), user.getConstraintsMap());
    }

    @Override
    public void update(User user) {
        safePersist.update(() -> userRepository.saveAndFlush(user), user.getConstraintsMap());
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
