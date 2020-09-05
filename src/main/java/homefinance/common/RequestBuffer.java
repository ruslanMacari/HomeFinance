package homefinance.common;

import javax.enterprise.context.RequestScoped;
import org.springframework.stereotype.Component;

@Component
@RequestScoped
public class RequestBuffer {

  private String viewName;

  public void setViewName(String viewName) {
    this.viewName = viewName;
  }

  public String getViewName() {
    return viewName;
  }
}
