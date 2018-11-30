package ruslan.macari.web.utils.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ruslan.macari.security.User;
import ruslan.macari.web.utils.CurrentUser;

@Component
public class CurrentUserImpl implements CurrentUser{
    
    private static Map<String, User> users = new HashMap();

    @Autowired
    @Qualifier("usersMap")
    @Override
    public void setUsers(Map<String, User> users) {
        CurrentUserImpl.users = users;
    }
    
    @Override
    public User get(String sessionId) {
        return users.get(sessionId);
    }

    @Override
    public void add(String sessionId, User user) {
        users.put(sessionId, user);
    }
    
    @Override
    public void remove(String sessionId) {
        users.remove(sessionId);
    }
    
    @Override
    public boolean isAdmin(String sessionId) {
        User user = users.get(sessionId);
        if (user == null) {
            return false;
        }
        return false;//user.isAdmin();
    }
    
    @Override
    public boolean exists(String sessionId) {
        return users.get(sessionId) != null;
    }
    
    @Override
    public int size() {
        return users.size();
    }
    
}
