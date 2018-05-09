import java.time.Instant;

public class File {
    private Instant date;
    private String file;

    public File(Instant theDate, String theFile){
        this.date = theDate;
        this.file = theFile;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}
