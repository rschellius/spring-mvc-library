package nl.avans.ivh5.example.springmvc.book;

import java.util.List;

/**
 *
 */
public interface BookRepositoryIF {

    public List<Book> findAll();

    public List<Book> findById(Long id);

    public Book create(final Book book);
}
