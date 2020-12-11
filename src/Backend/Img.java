package Backend;

public class Img {
    private int id;
    private String url;
    private int notes_id; 

    Img(){

    }
    
    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getNotes_id() {
        return notes_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNotes_id(int notes_id) {
        this.notes_id = notes_id;
    }
}



