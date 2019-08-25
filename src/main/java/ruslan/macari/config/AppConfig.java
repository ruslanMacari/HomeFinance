package ruslan.macari.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({"ruslan.macari.service", "ruslan.macari.security", "ruslan.macari.util"})
@PropertySource("classpath:app.properties")
@EnableJpaRepositories("ruslan.macari.service.repository")
public class AppConfig {

  private static final String DIALECT_KEY = "hibernate.dialect";
  private static final String SHOW_SQL_KEY = "hibernate.show_sql";
  private static final String HBM2DDLAUTO_KEY = "hibernate.hbm2ddl.auto";
  @Value("${db.driver}")
  private String driverClassName;
  @Value("${db.password}")
  private String password;
  @Value("${db.url}")
  private String url;
  @Value("${db.username}")
  private String username;
  @Value("${" + DIALECT_KEY + "}")
  private String dialect;
  @Value("${" + SHOW_SQL_KEY + "}")
  private String showSql;
  @Value("${" + HBM2DDLAUTO_KEY + "}")
  private String hbm2ddlAuto;

  public void setUrl(String url) {
    this.url = url;
  }

  public void setHbm2ddlAuto(String hbm2ddlAuto) {
    this.hbm2ddlAuto = hbm2ddlAuto;
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("ruslan.macari.domain", "ruslan.macari.security");
    entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
    return entityManagerFactoryBean;
  }

  private Properties getHibernateProperties() {
    Properties properties = new Properties();
    properties.put(DIALECT_KEY, dialect);
    properties.put(SHOW_SQL_KEY, showSql);
    properties.put(HBM2DDLAUTO_KEY, hbm2ddlAuto);
    return properties;
  }

  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }

}
