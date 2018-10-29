package ruslan.macari.web.exceptions;

import ruslan.macari.domain.User;

public class AccesException extends Exception{
    private final User user;

    public AccesException(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    
}
