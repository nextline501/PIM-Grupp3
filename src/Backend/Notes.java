package Backend;

public class Notes {
    private int id;
    private String text;
    private String date;

    Notes(){

    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
    
    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
