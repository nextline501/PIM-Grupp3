package Backend;

public class Notes {
    private int id;
    private String noteText;
    private String date;

    Notes(){

    }

    public int getId() {
        return id;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNoteText(String text) {
        this.noteText = text;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
