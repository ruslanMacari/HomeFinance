package ruslan.macari.dao;

import java.util.List;

import ruslan.macari.domain.User;

public interface UserDAO {

	public void addUser(User u);
	public void updateUser(User u);
	public List<User> listUsers();
        public List<User> listUsersLimit(int limit);
	public User getUserById(int id);
        public User getUserByName(String name);
	public void removeUser(int id);
}
