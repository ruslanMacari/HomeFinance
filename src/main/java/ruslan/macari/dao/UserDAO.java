package ruslan.macari.dao;

import java.util.List;

import ruslan.macari.models.User;

public interface UserDAO {

	public void addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserById(int id);
	public void removeUser(int id);
}
