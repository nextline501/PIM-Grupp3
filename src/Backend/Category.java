package Backend;

public class Category {
    private int id;
    private String category;

    Category(){

    }
    
    public int getId() {
            return id;
    }

    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
