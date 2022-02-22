package homefinance.common.beans;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class LocalDateAdapter {

  public LocalDate now() {
    return LocalDate.now();
  }
}
