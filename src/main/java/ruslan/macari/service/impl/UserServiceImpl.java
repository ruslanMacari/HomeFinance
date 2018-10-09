package ruslan.macari.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ruslan.macari.domain.User;
import ruslan.macari.repository.UserRepository;
import ruslan.macari.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User addUser(User user) {
        User savedUser = userRepository.saveAndFlush(user);
        return savedUser;
    }

    @Override
    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> listUsersLimit(int limit) {
        return userRepository.listUsersLimit(new PageRequest(0, limit));
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public void removeUser(int id) {
        userRepository.delete(id);
    }
   
}
