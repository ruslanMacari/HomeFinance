package homefinance.user.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Generated;

@Entity
@Table(name = "user_roles", uniqueConstraints = @UniqueConstraint(columnNames = {"role",
    "user_id"}, name = "duplicated_user_role"))
public class UserRole {

  private Integer id;
  private User user;
  private Role role;

  public UserRole() {
  }

  public UserRole(User user, Role role) {
    this.user = user;
    this.role = role;
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, length = 45)
  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  @Generated
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + Objects.hashCode(id);
    hash = 59 * hash + Objects.hashCode(user);
    hash = 59 * hash + Objects.hashCode(role);
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
    final UserRole other = (UserRole) obj;
    if (!Objects.equals(role, other.role)) {
      return false;
    }
    return Objects.equals(id, other.id);
  }

}