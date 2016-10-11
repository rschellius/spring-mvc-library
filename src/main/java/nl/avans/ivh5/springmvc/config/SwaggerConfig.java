package nl.avans.ivh5.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger is een manier om de API te documenteren. Dat gaat voor een groot deel automatisch.
 * Je kunt echter ook alle informatie aanpassen.
 * Je bekijkt de Swagger documentatie via http://localhost:8090/swagger-ui.html.
 *
 * Meer info: http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("nl.avans.ivh5.springmvc.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Avans Bibliotheek REST API")
                .description("Dit is de beschrijving van de voorbeeld Spring Bibliotheek Rest API. Het doel is om te laten zien hoe de REST API's inzichtelijk zijn gemaakt via Swagger.")
                .termsOfServiceUrl("http://www.avans.nl/")
                // .contact(new Contact("Jouw Naam", "https://bitbucket.org/AEI-informatica/", "jouw.naam@mail.com"))
                .license("Avans License Version 2.0")
                .licenseUrl("http://www.avans.nl/")
                .version("1.0.0")
                .build();
    }
}