package nl.avans.ivh5.springmvc.library.model;

public class Book {

    private final Long EAN;
    private final String title;
    private final String author;
    private final String shortDescription;
    private final String edition;
    private final String imageURL;
    private final int nrOfCopies;

    // Builder pattern
    public static class Builder {

        // Required parameters
        private final Long EAN;
        private final String title;
        private final String author;

        // Optional parameters
        private String edition = "";
        private String shortDescription = "";
        private String imageURL = "";
        private int nrOfCopies = 0;

        public Builder(Long EAN, String title, String author) {
            this.EAN = EAN;
            this.title = title;
            this.author = author;
        }

        public Builder edition(String edition) {
            this.edition = edition;
            return this;
        }

        public Builder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder imageUrl(String imageURL) {
            this.imageURL = imageURL;
            return this;
        }

        public Builder nrOfCopies(int copies) {
            this.nrOfCopies = copies;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    private Book(Builder builder){
        this.EAN = builder.EAN;
        this.title = builder.title;
        this.author = builder.author;
        this.shortDescription = builder.shortDescription;
        this.edition = builder.edition;
        this.imageURL = builder.imageURL;
        this.nrOfCopies = builder.nrOfCopies;
    }

    public Long getEAN() {
        return EAN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getEdition() {
        return edition;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getNrOfCopies() { return nrOfCopies; }

    @Override
    public String toString(){
        return "Book(EAN = " + this.getEAN() + ", Title = '" + this.getTitle() + "')";
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

        return getEAN() == book.getEAN();
    }

    /**
     * Deze methode zorgt dat we een Copy in een Hashlist kunnen opslaan. Maken we
     * momenteel nog geen gebruik van.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return (int) (getEAN() ^ (getEAN() >>> 32));
    }
}
