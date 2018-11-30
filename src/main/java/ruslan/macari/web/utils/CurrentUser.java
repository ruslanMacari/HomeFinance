package ruslan.macari.web.utils;

import java.util.Map;
import ruslan.macari.security.User;

public interface CurrentUser {
    
    void setUsers(Map<String, User> users);
    
    User get(String sessionId);

    void add(String sessionId, User user);
    
    void remove(String sessionId);
    
    boolean isAdmin(String sessionId);
    
    boolean exists(String sessionId);
    
    int size();
    
}
