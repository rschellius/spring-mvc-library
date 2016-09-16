package nl.avans.ivh5.example.springmvc.copy;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 */
public class Copy {

    private long copyID;
    private Date updatedDate;
    private long ISBN;

    public Copy(long ISBN) {

        Date now = new Date();
        this.updatedDate = new Timestamp(now.getTime());
        this.ISBN = ISBN;
        this.copyID = 0;
    }

    public Copy() {
        this.copyID = 0;
    }

    public long getCopyID() {
        return copyID;
    }

    public void setCopyID(int copyID) {
        this.copyID = copyID;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

}
