package homefinance;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import java.io.File;
import java.io.IOException;
import javax.sql.DataSource;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Ignore
public class TestConfiguration {

  @Value("${embedded-pg.port}")
  private int port;

  @Bean
  public DataSource dataSource() throws IOException {
    return EmbeddedPostgres.builder()
        .setPort(this.port)
        .setConnectConfig("sslmode", "disable")
        .setOverrideWorkingDirectory(new File("target/embedded-pg"))
        .start().getPostgresDatabase();
  }

}