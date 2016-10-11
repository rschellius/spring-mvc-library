package nl.avans.ivh5.springmvc.library.repository;

import nl.avans.ivh5.springmvc.library.model.Book;

import java.util.List;

/**
 *
 */
public interface BookRepositoryIF {

    public List<Book> findAll();

    public List<Book> findByEAN(Long id);

    public Book create(final Book book);
}
