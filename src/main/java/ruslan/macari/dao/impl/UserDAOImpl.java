package ruslan.macari.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruslan.macari.dao.UserDAO;

import ruslan.macari.models.User;

@Service
public class UserDAOImpl implements UserDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

        @Autowired
        @Qualifier(value = "hibernate5AnnotatedSessionFactory")
	private SessionFactory sessionFactory;
	
//	public void setSessionFactory(SessionFactory sf){
//		this.sessionFactory = sf;
//	}

	@Override
        @Transactional
	public void addUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(user);
		LOGGER.info("User saved successfully, User Details="+user);
	}

	@Override
        @Transactional
	public void updateUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(user);
		LOGGER.info("User updated successfully, User Details="+user);
	}

	@SuppressWarnings("unchecked")
	@Override
        @Transactional
	public List<User> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
                List<User> userList = session.createQuery("from User").list();
		for(User user : userList){
			LOGGER.info("User List::"+user);
		}
		return userList;
	}

	@Override
        @Transactional
	public User getUserById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		User user = (User) session.load(User.class, id);
		LOGGER.info("User loaded successfully, User details="+user);
		return user;
	}

	@Override
        @Transactional
	public void removeUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		User user = (User) session.load(User.class, id);
		if(null != user){
			session.delete(user);
		}
		LOGGER.info("User deleted successfully, user details="+user);
	}

    @Override
    @Transactional
    public User getUserByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        String sql = "select * from users where name=:name";
        NativeQuery query = session.createNativeQuery(sql).addEntity(User.class);
        query.setParameter("name", name);
        List<User> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    @Transactional
    public List<User> listUsersLimit(int limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User");
        query.setMaxResults(limit);
        List<User> userList = query.list();
        for (User user : userList) {
            LOGGER.info("User List::" + user);
        }
        return userList;
    }

}
