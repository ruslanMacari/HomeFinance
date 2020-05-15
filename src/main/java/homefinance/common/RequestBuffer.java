package homefinance.common;

import javax.enterprise.context.RequestScoped;
import org.springframework.stereotype.Component;

@Component
@RequestScoped
public class RequestBuffer {

  private String url;

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return this.url;
  }
}
