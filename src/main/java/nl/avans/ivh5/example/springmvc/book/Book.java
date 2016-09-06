package nl.avans.ivh5.example.springmvc.book;

import org.springframework.beans.factory.annotation.Autowired;

public class Book {

    private final long id;
    private final String content;
    private final String title;
    private final String author;

    @Autowired
    public Book(long id, String content, String title, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.title = title;
    }

    /**
     * Wanneer we boeken in de boekenlijst opzoeken moeten we een
     * boek hebben met het id dat we zoeken. De overige attributen zijn niet van belang
     * omdat ze niet meedoen in de vergelijking van de twee boeken.
     * Zie BookController.validateId()
     *
     * @param id
     */
    @Autowired
    public Book(long id) {
        this.id = id;
        this.content = this.title = this.author = null;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    /**
     * Om een boek op id terug te kunnen vinden in de ArrayList moeten we een
     * vergelijkingsmethode maken die aangeeft wanneer boeken gelijk zijn aan elkaar.
     * Wij zeggen dat twee boeken gelijk zijn wanneer ze dezelfde id hebben.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        return getId() == book.getId();
    }

    /**
     * Deze methode zorgt dat we een Book in een Hashlist kunnen opslaan. Maken we
     * momenteel nog geen gebruik van.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}
