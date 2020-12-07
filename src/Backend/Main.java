package Backend;

import java.nio.file.Paths;
import java.util.List;
import express.Express;
import express.middleware.Middleware;

public class Main {
    public static void main(String[] args) {
        Express app = new Express();
        Database db = new Database();

        try {
            app.use(Middleware.statics(Paths.get("src/Frontend").toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        app.listen(5501);
        System.out.println("Server started on port: 5501");

    }
}