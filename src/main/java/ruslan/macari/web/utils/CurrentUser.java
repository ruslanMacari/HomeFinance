package ruslan.macari.web.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ruslan.macari.domain.User;

@Component
public class CurrentUser {
    
    private static Map<String, User> users = new HashMap();

    @Autowired
    @Qualifier("usersMap")
    public static void setUsers(Map<String, User> users) {
        CurrentUser.users = users;
    }
    
    public static User get(String sessionId) {
        return users.get(sessionId);
    }

    public static void add(String sessionId, User user) {
        users.put(sessionId, user);
    }
    
    public static void remove(String sessionId) {
        users.remove(sessionId);
    }
    
    public static boolean isAdmin(String sessionId) {
        User user = users.get(sessionId);
        if (user == null) {
            return false;
        }
        return user.isAdmin();
    }
    
    public static boolean exists(String sessionId) {
        return users.get(sessionId) != null;
    }
    
    public static int size() {
        return users.size();
    }
    
}
