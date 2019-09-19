package ruslan.macari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class HomeFinanceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HomeFinanceApplication.class, args);
  }

}
