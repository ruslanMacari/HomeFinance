package homefinance.common;

import homefinance.common.util.PathSelector;
import homefinance.common.exception.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class CommonController<T> {

  protected PathSelector pathSelector;

  public static String getRedirectURL(String URL) {
    return "redirect:" + URL;
  }

  @Autowired
  public void setPathSelector(PathSelector pathSelector) {
    this.pathSelector = pathSelector;
  }

  public void test(T t) {
    if (t == null) {
      throw new PageNotFoundException();
    }
  }
}
