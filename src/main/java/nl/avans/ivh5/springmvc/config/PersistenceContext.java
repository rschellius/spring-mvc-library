package nl.avans.ivh5.springmvc.config;

import nl.avans.ivh5.springmvc.library.repository.BookRepository;
import nl.avans.ivh5.springmvc.library.repository.CopyRepository;
import nl.avans.ivh5.springmvc.library.repository.LoanRepository;
import nl.avans.ivh5.springmvc.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author Petri Kainulainen
 *
 * Dit bestand bevat database settings voor het starten van de applicatie.
 */
@SuppressWarnings("Duplicates")
@Profile("default")
@Configuration
@EnableTransactionManagement
public class PersistenceContext {

    protected static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    protected static final String PROPERTY_NAME_DATABASE_PASSWORD = "test";
    protected static final String PROPERTY_NAME_DATABASE_URL = "jdbc:mysql://localhost:3306/library";
    protected static final String PROPERTY_NAME_DATABASE_USERNAME = "spring";

    private static final String PROPERTY_PACKAGES_TO_SCAN = "nl.avans.ivh5.springmvc.config";

    @Resource
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
        dataSource.setUrl(PROPERTY_NAME_DATABASE_URL);
        dataSource.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
        dataSource.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);

        return dataSource;
    }

    @Bean
    @Qualifier("PersistenceContext")
    @Primary
    public BookRepository getBookRepository() {
        return new BookRepository(this.dataSource());
    }

    @Bean
    public CopyRepository getCopyRepository() {
        return new CopyRepository(this.dataSource());
    }

    @Bean
    public MemberRepository getMemberRepository() {
        return new MemberRepository(this.dataSource());
    }

    @Bean
    public LoanRepository getLoanRepository() {
        return new LoanRepository(this.dataSource());
    }

}
