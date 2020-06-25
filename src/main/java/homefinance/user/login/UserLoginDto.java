package homefinance.user.login;

import java.util.Objects;
import javax.validation.constraints.Size;

public class UserLoginDto {

  private String name;
  private String password;

  @Size(min = 3, message = "{MinSize.user.name}")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Size(min = 4, message = "{MinSize.user.password}")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "UserLoginModel{" +
        "name='" + name + '\'' +
        ", password='" + password + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserLoginDto that = (UserLoginDto) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, password);
  }
}
