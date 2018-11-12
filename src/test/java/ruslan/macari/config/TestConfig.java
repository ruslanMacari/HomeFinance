package ruslan.macari.config;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:app.properties")
public class TestConfig extends AppConfig {

    @Value("${db.url.test}")
    private String url;
    
    @Value("${hibernate.hbm2ddl.auto.test}")
    private String hbm2ddlAuto;
    
    @PostConstruct
    public void init() {
        super.setUrl(url);
        super.setHbm2ddlAuto(hbm2ddlAuto);
    }
    
}
