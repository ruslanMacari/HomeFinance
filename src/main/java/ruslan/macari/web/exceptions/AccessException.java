package ruslan.macari.web.exceptions;

import ruslan.macari.domain.User;

public class AccessException extends Exception{

    private static final long serialVersionUID = 1734407919865014791L;
    private final User user;
    
    public AccessException(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    
}
