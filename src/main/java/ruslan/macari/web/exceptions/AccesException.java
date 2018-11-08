package ruslan.macari.web.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import ruslan.macari.domain.User;
import ruslan.macari.web.utils.CurrentUser;

public class AccesException extends Exception{

    private static final long serialVersionUID = 1734407919865014791L;
    private final User user;
    private CurrentUser currentUser;

    @Autowired
    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }
    
    public AccesException(String sessionId) {
        if(currentUser.exists(sessionId)) {
            this.user = currentUser.get(sessionId);
        } else {
            this.user = new User("Unauthorized");
        }
    }

    public User getUser() {
        return user;
    }
    
}
