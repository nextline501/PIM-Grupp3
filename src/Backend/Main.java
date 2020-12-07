package Backend;

import java.nio.file.Paths;
//import java.util.List;
import express.Express;
import express.middleware.Middleware;

public class Main {
    public static void main(String[] args) {
        Express app = new Express();
        Database db = new Database();

        app.post("/rest/notes", (req, res) -> {
            Notes note = (Notes) req.getBody(Notes.class);
            System.out.println("this is my note " + note);
            db.createNote(note);
            res.send("post ok");
        });

        try {
            app.use(Middleware.statics(Paths.get("src/Frontend").toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        app.listen(5501);
        System.out.println("Server started on port: 5501");
    }
}