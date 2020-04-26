package homefinance.common;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import java.io.File;
import java.io.IOException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedDataSourceConfiguration {

  @Bean
  public DataSource dataSource() throws IOException {
    return EmbeddedPostgres.builder()
        .setPort(54321)
        .setConnectConfig("sslmode", "disable")
        .setOverrideWorkingDirectory(new File("target/embedded-pg"))
        .start().getPostgresDatabase();
  }

}