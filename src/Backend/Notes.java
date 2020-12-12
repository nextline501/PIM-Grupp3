package Backend;

public class Notes {
    private int id;
    private String title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", noteText='" + noteText + '\'' +
                '}';
    }
}
