package homefinance.user;

public interface UserFields {

  default Long getId() {
    return null;
  }

  String getName();
  String getPassword();

  default boolean isAdmin() {
    return false;
  }
}
