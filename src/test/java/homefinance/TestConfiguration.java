package homefinance;

import javax.sql.DataSource;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Disabled
public class TestConfiguration {

  @Bean
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .driverClassName("org.h2.Driver")
        .url("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH")
        .username("sa")
        .password("")
        .build();
  }

}
