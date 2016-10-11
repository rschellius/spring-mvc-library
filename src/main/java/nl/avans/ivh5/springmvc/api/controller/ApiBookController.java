package nl.avans.ivh5.springmvc.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nl.avans.ivh5.springmvc.library.model.Book;
import nl.avans.ivh5.springmvc.library.service.BookServiceIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
// Hier geef je de mapping die geldt voor alle endpoints in deze controller.
// Wordt voor aan URL toegevoegd.
@RequestMapping("/api/book")
public class ApiBookController {

    private static final Logger log = LoggerFactory.getLogger(ApiBookController.class);

    @Autowired
    private BookServiceIF bookService;

    /**
     * Constructor
     *
     * @param bookService
     */
    public ApiBookController(BookServiceIF bookService){
        this.bookService = bookService;
    }

    /**
     * Retourneer alle boeken in de repository.
     *
     * @return
     */
    @ApiOperation(value = "Find all books", notes = "Find all books in the system and return them as a list.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Book> findAllBooks() {
        log.debug("findAllBooks called");

        List<Book> books = bookService.findAll();
        return books;
    }

    /**
     * Vind een boek op ID.
     * De @Api informatie in de annotaties wordt als documentatie getoond als je in de Swagger UI kijkt.
     *
     * Start de app en ga daarvoor naar http://localhost:8090/swagger-ui.html
     *
     * @param ean
     * @return
     */
    @ApiOperation(value = "Find by EAN", notes = "Find a book based on its EAN.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Book.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value = "/{ean}", method = RequestMethod.GET)
    public Book findByEAN(@ApiParam(name = "ean", value = "European Article Number", required = true) @PathVariable Long ean) throws Exception{
        log.debug("findByEAN ean = " + ean);

        Book book;

        try{
         book = bookService.findByEAN(ean);
        } catch(Exception ex) {
            // Deze exception wordt afgehandeld door de RestResponseEntityExceptionHandler
            // Zie ook http://www.baeldung.com/2013/01/31/exception-handling-for-rest-with-spring-3-2/
            // Eigenlijk zou je hier BookNotFoundException willen throwen - moet je zelf maken.
            log.error("findById did not find ean");
            throw new Exception("A book with ean = " + ean + " was not found!");
        }
        return book;
    }

}
