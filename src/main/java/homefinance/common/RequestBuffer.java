package homefinance.common;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestBuffer {

  private String viewName;

  public void setViewName(String viewName) {
    this.viewName = viewName;
  }

  public String getViewName() {
    return viewName;
  }
}
