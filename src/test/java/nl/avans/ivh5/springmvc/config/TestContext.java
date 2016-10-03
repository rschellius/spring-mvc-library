package nl.avans.ivh5.springmvc.config;

import nl.avans.ivh5.springmvc.library.service.BookService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petri Kainulainen
 */
@Configuration
public class TestContext {

    @Bean
    public BookService bookService() {
        return Mockito.mock(BookService.class);
    }

//    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//
//        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
//        messageSource.setUseCodeAsDefaultMessage(true);
//
//        return messageSource;
//    }

}
