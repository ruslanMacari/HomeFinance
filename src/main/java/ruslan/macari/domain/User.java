package ruslan.macari.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        
    }   
    
    //@Size(min = 3, message = Values.nameMessage)
    private String name;

    //@Size(min = 4, message = passMessage)
    private String password;
    
    private boolean admin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", password=" + password + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return this.id == other.id;
    }

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(int id) {
        this.id = id;
    }
    
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
}

@PropertySource("classpath:messages.properties")
class Values {
    @Value("${MinSize.user.name}")
    private static String name;
    
    public static final String nameMessage = name;
    
    @Value("${MinSize.user.password}")
    public static String passMessage;
}
