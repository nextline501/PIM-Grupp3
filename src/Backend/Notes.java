package Backend;

public class Notes {
    private int id;
    private String noteText;

    Notes(){

    }

    public int getId() {
        return id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNoteText(String text) {
        this.noteText = text;
    }
}
