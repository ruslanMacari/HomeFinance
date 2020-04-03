package homefinance.user.entity;

import homefinance.common.entity.ConstraintEntity;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import lombok.Generated;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name", name = User.UNIQUE_CONSTRAINT_NAME)})
public class User extends ConstraintEntity {

  public static final String UNIQUE_CONSTRAINT_NAME = "duplicated_user_name";
  private Integer id;
  private String name;
  private String password;
  private boolean enabled = true;
  private Set<UserRole> userRole = new HashSet<>(0);

  {
    constraintsMap = new HashMap<>();
    constraintsMap.put("name", UNIQUE_CONSTRAINT_NAME);
  }

  public User() {

  }

  public User(String name) {
    this.name = name;
  }

  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Size(min = 3, message = "{MinSize.user.name}")
  @Column(name = "name", nullable = false, length = 45)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Size(min = 4, message = "{MinSize.user.password}")
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

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user",
      cascade = CascadeType.ALL, orphanRemoval = true)
  public Set<UserRole> getUserRole() {
    return userRole;
  }

  public void setUserRole(Set<UserRole> userRole) {
    this.userRole = userRole;
  }

  public void setOneRole(String role) {
    if (userRole.isEmpty()) {
      UserRole roleUser = new UserRole(this, role);
      userRole.add(roleUser);
    } else {
      boolean first = true;
      for (UserRole userRoleItem : userRole) {
        if (first) {
          userRoleItem.setRole(role);
          first = false;
        } else {
          userRole.remove(userRoleItem);
        }
      }
    }
  }

  public boolean hasAdmin() {
    return userRole.stream().anyMatch((role) -> (role.getRole().equals(Role.ADMIN)));
  }

  @Override
  @Generated
  public String toString() {
    return "User{"
        + "id=" + id
        + ", name=" + name
        + ", password=" + password
        + '}';
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 43 * hash + Objects.hashCode(id);
    hash = 43 * hash + Objects.hashCode(name);
    hash = 43 * hash + Objects.hashCode(password);
    hash = 43 * hash + (enabled ? 1 : 0);
    return hash;
  }

  @Override
  @Generated
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
    return Objects.equals(id, other.id);
  }

}
