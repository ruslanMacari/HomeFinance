package ruslan.macari.service;

import java.util.List;
import ruslan.macari.models.User;

public interface UserService {

	public void addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserById(int id);
	public void removeUser(int id);
        public User getUserByName(String name);
        public List<User> listUsersLimit(int limit);
	
}
