package ruslan.macari.web.exceptions;

import ruslan.macari.domain.User;

public class AccesException extends Exception{

    private static final long serialVersionUID = 1734407919865014791L;
    private final User user;

    public AccesException(boolean exists, User user) {
        if(exists) {
            this.user = user;
        } else {
            this.user = new User("Unauthorized");
        }
    }

    public User getUser() {
        return user;
    }
    
}
