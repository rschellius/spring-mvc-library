package nl.avans.ivh5.springmvc.api.controller;

import io.swagger.annotations.*;
import nl.avans.ivh5.springmvc.common.exception.BookNotFoundException;
import nl.avans.ivh5.springmvc.common.exception.LoanNotCreatedException;
import nl.avans.ivh5.springmvc.library.model.Loan;
import nl.avans.ivh5.springmvc.library.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
// Hier geef je de mapping die geldt voor alle endpoints in deze controller.
// Wordt voor aan URL toegevoegd.
@RequestMapping("/api/loan")
@Api(value = "api value", tags = {"Loans"})
@ApiModel(value = "Modelnaam", description = "Beschrijving van het model")
public class ApiLoanController {

    private static final Logger log = LoggerFactory.getLogger(ApiLoanController.class);

    @Autowired
    private LoanService loanService;

    /**
     * Constructor
     *
     * @param loanService
     */
    public ApiLoanController(LoanService loanService){
        this.loanService = loanService;
    }

    /**
     *
     *
     * @return
     */
    @ApiOperation(value = "Create loan", notes = "Create a new loan.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Loan.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value = "/", headers = "Accept=application/json", method = RequestMethod.POST)
    public Loan createLoan(Loan loan) throws LoanNotCreatedException {
        log.debug("createLoan");

        Loan result = null;
        try {
            result = loanService.createLoan(loan);
        } catch (Exception ex) {
            log.error("Exception in createLoan: " + ex.getMessage());
            throw ex;
        }
        return result;
    }

    /**
     * Vind een boek op ID.
     * De @Api informatie in de annotaties wordt als documentatie getoond als je in de Swagger UI kijkt.
     *
     * Start de app en ga daarvoor naar http://localhost:8090/swagger-ui.html
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Find by member ID", notes = "Find loans by member ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value = "/{id}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ArrayList<Loan> findLoansByMemberId(@ApiParam(name = "ean", value = "European Article Number", required = true) @PathVariable int id)
            throws BookNotFoundException {
        log.debug("findLoansByMemberId id = " + id);

        try{
            return loanService.findLoansByMemberId(id);
        } catch(Exception ex) {
            // Deze exception wordt afgehandeld door de RestResponseEntityExceptionHandler
            // Zie ook http://www.baeldung.com/2013/01/31/exception-handling-for-rest-with-spring-3-2/
            log.error("findByEAN did not find ean");
            throw new BookNotFoundException("No loans found for member with id = " + id);
        }
    }

}
