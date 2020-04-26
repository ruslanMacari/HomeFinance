package homefinance;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import java.io.File;
import java.io.IOException;
import javax.sql.DataSource;
import org.junit.Ignore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Ignore
public class TestConfiguration {

  @Bean
  public DataSource dataSource() throws IOException {
    return EmbeddedPostgres.builder()
        .setOverrideWorkingDirectory(new File("target/embedded-pg"))
        .start().getPostgresDatabase();
  }

}