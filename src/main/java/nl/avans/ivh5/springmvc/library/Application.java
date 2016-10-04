package nl.avans.ivh5.springmvc.library;

import nl.avans.ivh5.springmvc.config.ApplicationConfig;
import nl.avans.ivh5.springmvc.config.ApplicationContext;
import nl.avans.ivh5.springmvc.config.PersistenceContext;
import nl.avans.ivh5.springmvc.config.ProductionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

/**
 * Dit is een van de configuratiebestanden die vanuit de Application.java class worden
 * aangeroepen om je applicatie te initialiseren.
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = {"nl.avans.ivh5.springmvc"})
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        // Enable Application configuration. The followinng files will be used.
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ApplicationConfig.class);
        ctx.register(ApplicationContext.class);
        ctx.register(PersistenceContext.class);
        ctx.register(ProductionContext.class);

        SpringApplication.run(Application.class);

        // Je kunt Bean uit de Ctx Context opvragen. Soms is dat nodig om bv een nieuw
        // object te kunnen maken - bv bij XYZRepository.
        // DriverManagerDataSource driverMgr = ctx.getBean(DriverManagerDataSource.class);
        // logger.info("Database driver URL = "+ driverMgr.getUrl() + " usernem = " + driverMgr.getUsername());
    }

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

}
