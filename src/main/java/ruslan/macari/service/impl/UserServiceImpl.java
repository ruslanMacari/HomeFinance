package ruslan.macari.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruslan.macari.security.User;
import ruslan.macari.service.repository.UserRepository;
import ruslan.macari.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User add(User user) {
        User savedUser = userRepository.saveAndFlush(user);
        return savedUser;
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
    public List<User> listLimit(int limit) {
        return userRepository.listLimit(new PageRequest(0, limit));
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public void delete(String id) {
        userRepository.delete(id);
    }

    @Override
    public User getRoot() {
        return userRepository.findByName("root");
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
    public User getByNameExceptID(String name, int id) {
        //return userRepository.getByNameExceptID(name, id);
        return null;
    }

    @Override
    public List<User> usersExceptRoot() {
        return userRepository.usersExceptRoot("root");
    }
   
}
