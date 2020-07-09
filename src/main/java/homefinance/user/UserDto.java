package homefinance.user;

import homefinance.user.login.UserLoginDto;

public class UserDto extends UserLoginDto {

  private Integer id;
  private boolean admin;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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
        "} " + super.toString();
  }
}
