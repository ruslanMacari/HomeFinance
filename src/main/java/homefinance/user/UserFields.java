package homefinance.user;

public interface UserFields {

  default Integer getId() {
    return null;
  }

  String getName();
  String getPassword();

  default boolean isAdmin() {
    return false;
  }
}
