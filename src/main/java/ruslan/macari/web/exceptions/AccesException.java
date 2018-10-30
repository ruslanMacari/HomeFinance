package ruslan.macari.web.exceptions;

import ruslan.macari.domain.User;
import ruslan.macari.web.utils.CurrentUser;

public class AccesException extends Exception{

    private static final long serialVersionUID = 1734407919865014791L;
    private final User user;

    public AccesException(String sessionId) {
        if(CurrentUser.exists(sessionId)) {
            this.user = CurrentUser.get(sessionId);
        } else {
            this.user = new User("Unauthorized");
        }
    }

    public User getUser() {
        return user;
    }
    
}
