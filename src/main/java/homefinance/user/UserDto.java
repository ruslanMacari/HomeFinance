package homefinance.user;

import homefinance.user.login.UserLoginDto;

public class UserDto extends UserLoginDto {

  private Long id;
  private boolean admin;
  private boolean passwordChanged;
  private boolean loggedIn;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  @Override
  public String toString() {
    return "UserDto{" +
        "id=" + id +
        ", admin=" + admin +
        ", passwordChanged=" + passwordChanged +
        ", loggedIn=" + loggedIn +
        "} " + super.toString();
  }

  public boolean isPasswordChanged() {
    return passwordChanged;
  }

  public void setPasswordChanged(boolean passwordChanged) {
    this.passwordChanged = passwordChanged;
  }

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }
}
