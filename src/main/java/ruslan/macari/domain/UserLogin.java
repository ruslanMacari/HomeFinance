package ruslan.macari.domain;

import org.springframework.stereotype.Component;

@Component
public class UserLogin extends User {

    public UserLogin(String name, String password) {
        super(name, password);
    }

    public UserLogin() {
    }
    
}
