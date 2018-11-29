package ruslan.macari.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

    private String name;
    private String password;
    private boolean enabled;
    private Set<UserRole> userRole = new HashSet<>(0);
    
    @Id
    @Column(name = "name", unique = true, nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "password", nullable = false, length = 60)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
    
    @Override
    public String toString() {
        return "User{" 
                + "name=" + name 
                + ", password=" + password 
                + '}';
    }

    public User() {
        enabled = true;
    }

    public User(String name) {
        this.name = name;
        enabled = true;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        enabled = true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(name);
        hash = 37 * hash + Objects.hashCode(password);
        hash = 37 * hash + (enabled ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(userRole);
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
        if (enabled != other.enabled) {
            return false;
        }
        if (!Objects.equals(name, other.name)) {
            return false;
        }
        if (!Objects.equals(password, other.password)) {
            return false;
        }
        return Objects.equals(userRole, other.userRole);
    }
    
}
