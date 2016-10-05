package nl.avans.ivh5.springmvc.config;

import nl.avans.ivh5.springmvc.library.service.MemberService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petri Kainulainen
 */
@Configuration
public class TestContext {

    public static final String MEMBER_FIRSTNAME = "Voornaam";
    public static final String MEMBER_LASTNAME = "Achternaam";
    public static final String MEMBER_STREET = "Straatnaam";
    public static final String MEMBER_HOUSENUMBER = "123";
    public static final String MEMBER_CITY = "Stadnaam";
    public static final String MEMBER_PHONENUMBER = "06-12345678";
    public static final String MEMBER_EMAILADDRESS_VALID = "test.user@server.com";
    public static final String MEMBER_EMAILADDRESS_INVALID = "_invalid_email_";

//    @Bean
//    public BookService bookService() {
//        return Mockito.mock(BookService.class);
//    }

    @Bean
    public MemberService memberService() {
        return Mockito.mock(MemberService.class);
    }
}
