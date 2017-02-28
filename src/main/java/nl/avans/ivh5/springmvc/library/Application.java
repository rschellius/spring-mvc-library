package nl.avans.ivh5.springmvc.library;

import nl.avans.ivh5.springmvc.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
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
        ctx.register(SwaggerConfig.class);

        SpringApplication.run(Application.class);
    }

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    /**
     * Include GIT properties in our application.
     * @see "http://www.baeldung.com/spring-git-information"
     *
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {

        PropertySourcesPlaceholderConfigurer propsConfig
                = new PropertySourcesPlaceholderConfigurer();

        propsConfig.setLocation(new ClassPathResource("git.properties"));
        propsConfig.setIgnoreResourceNotFound(false);
        propsConfig.setIgnoreUnresolvablePlaceholders(true);

        return propsConfig;
    }

    /**
     * The th:text=”#{key}” tag attribute can be used to display values from property files.
     * For this to work the property file must be configured as messageSource bean
     */
    @Bean
    @Description("Spring Message Resolver")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // messageSource.setBasename("messages");
        messageSource.setBasename("git");
        return messageSource;
    }
}
