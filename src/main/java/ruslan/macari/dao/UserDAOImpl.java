package ruslan.macari.dao;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ruslan.macari.models.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(user);
		LOGGER.info("User saved successfully, User Details="+user);
	}

	@Override
	public void updateUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(user);
		LOGGER.info("User updated successfully, User Details="+user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
                List<User> userList = session.createQuery("select * from User").list();
		for(User user : userList){
			LOGGER.info("User List::"+user);
		}
		return userList;
	}

	@Override
	public User getUserById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		User user = (User) session.load(User.class, id);
		LOGGER.info("User loaded successfully, User details="+user);
		return user;
	}

	@Override
	public void removeUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		User user = (User) session.load(User.class, id);
		if(null != user){
			session.delete(user);
		}
		LOGGER.info("User deleted successfully, user details="+user);
	}

    @Override
    public User getUserByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        String sql = "select * from users where name=:name";
        NativeQuery query = session.createNativeQuery(sql).addEntity(User.class);
        query.setParameter("name", name);
        List<User> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        }
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<User> query1 = criteriaBuilder.createQuery(User.class);
//        Root<User> root = query1.from(User.class);
//        query1.select(root.get("name"));
        return list.get(0);
    }

    @Override
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
