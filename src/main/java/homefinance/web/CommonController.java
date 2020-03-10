package homefinance.web;

import homefinance.util.PathSelector;
import homefinance.web.exceptions.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class CommonController<T> {

  protected PathSelector pathSelector;

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
