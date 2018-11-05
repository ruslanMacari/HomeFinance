package ruslan.macari.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ruslan.macari.domain.User;
import ruslan.macari.service.repository.UserRepository;
import ruslan.macari.service.UserService;

@Service
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
    public User getById(int id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public User getAdmin() {
        List<User> list = userRepository.findAdmins();
        return list.isEmpty() ? null : list.get(0);
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
        return userRepository.getByNameExceptID(name, id);
    }
   
}
