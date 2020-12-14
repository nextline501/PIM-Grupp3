package Backend;

public class UrlHolder {
    private int id;
    private String url;

    UrlHolder(){
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }
    
    public String getUrl() {
        return url;
    }
}
