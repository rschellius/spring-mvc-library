package nl.avans.ivh5.springmvc.library.service;

import nl.avans.ivh5.springmvc.common.exception.BookNotFoundException;
import nl.avans.ivh5.springmvc.library.model.Book;
import nl.avans.ivh5.springmvc.library.model.Copy;
import nl.avans.ivh5.springmvc.library.model.Loan;
import nl.avans.ivh5.springmvc.library.model.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Dit is de interface voor boeken. Deze class bevindt zich logisch gezien in de business logic layer.
 * Controllers die informatie over boeken nodig hebben, doen dat door interactie met de BookService.
 * Er zou dus nooit direct een aanroep mogen zijn vanuit een controller naar een DAO (Repository in ons geval).
 */
public interface BookServiceIF {

    public ArrayList<Book> listBooksAtRESTServer();
    public List<Book> findAll();
    public Book findByEAN(Long ean) throws BookNotFoundException;
    public Book create(final Book book);
    public Loan lendBook(Loan loan);
    public List<Member> findAllMembers();
    public List<Copy> findLendingInfoByBookEAN(Long ean);

}
