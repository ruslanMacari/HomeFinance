package homefinance;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres.Builder;
import java.io.File;
import java.util.function.Consumer;
import javax.sql.DataSource;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Ignore
public class TestConfiguration {

//  @Bean
//  public DataSource dataSource() {
//    return DataSourceBuilder.create()
//        .driverClassName("org.h2.Driver")
//        .url("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1")
//        .username("sa")
//        .password("")
//        .build();
//  }

  @Bean
  public Consumer<Builder> embeddedPostgresCustomizer(
      @Value("${embedded-pg.directory}") String directory) {
    return builder -> builder.setServerConfig("ssl", "false")
        .setOverrideWorkingDirectory(new File(directory));
        //.setConnectConfig("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        //.setServerConfig("sslmode", "disable");
  }

}