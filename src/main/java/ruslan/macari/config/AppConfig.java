package ruslan.macari.config;

import java.util.Properties;
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
@ComponentScan("ruslan.macari.service")
@PropertySource("classpath:app.properties")
@EnableJpaRepositories("ruslan.macari.service.repository")
public class AppConfig {

    @Value("${db.driver}")
    private String driverClassName;
    
    @Value("${db.password}")
    private String password;
    
    @Value("${db.url}")
    private String url;
    
    @Value("${db.username}")
    private String username;
    
    private final String dialectKey = "hibernate.dialect";
    
    @Value("${" + dialectKey + "}")
    private String dialect;
    
    private final String showSqlKey = "hibernate.show_sql";
    
    @Value("${" + showSqlKey + "}")
    private String showSql;
    
    @Value("${entitymanager.packages.to.scan}")
    private String pacakgesToScan;
    
    private final String hbm2ddlAutoKey = "hibernate.hbm2ddl.auto";
    
    @Value("${" + hbm2ddlAutoKey + "}")
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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(pacakgesToScan);
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
        return entityManagerFactoryBean;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(dialectKey, dialect);
        properties.put(showSqlKey, showSql);
        properties.put(hbm2ddlAutoKey, hbm2ddlAuto);
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
    
}
