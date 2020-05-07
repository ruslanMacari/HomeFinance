package homefinance.common;

import javax.enterprise.context.RequestScoped;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequestScoped
public class RequestBuffer {

  private String viewName;
  private BindingResult errors;

  public void setViewNameAndErrors(String viewName, BindingResult errors) {
    this.viewName = viewName;
    this.errors = errors;
  }

  public BindingResult getErrors() {
    return this.errors;
  }

  public String getViewName() {
    return this.viewName;
  }
}
