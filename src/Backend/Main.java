package Backend;

import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import express.Express;
import express.middleware.Middleware;


public class Main {
    public static void main(String[] args) {
        Express app = new Express();
        Database db = new Database();

        app.get("/rest/notes", (req, res) -> {
            List<Notes> note = db.getNotes();
            res.json(note);
        });

        app.get("/rest/notes/:id", (req, res) ->{
            int id = Integer.parseInt(req.getParam("id"));
            List<UrlHolder> urls = db.getImgUrl(id);
            res.json(urls); 
        });

        app.post("/rest/create-post", (req, res) -> {
            Notes note = (Notes) req.getBody(Notes.class);
            System.out.println("this is my note " + note);
            db.createNote(note);
            res.send("post ok");
        });

        app.post("/rest/delete", (req, res) ->{
            Notes note = (Notes) req.getBody(Notes.class);
            db.deleteNote(note);
            res.send("delete post ok");
        });

        app.post("/rest/update", (req, res)->{
            Notes note = (Notes) req.getBody(Notes.class);
            db.updateNotes(note);
            res.send("update ok");
        });

        app.post("/api/file-upload", (req, res)->{
            String imgUrl = null;

            try {
                List<FileItem> imgFilesList = req.getFormData("filesKek");
                imgUrl = db.uploadImage(imgFilesList.get(0));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("File uploaded with url: "+ imgUrl);
            System.out.println("hit file up-load 5555");

            res.send(imgUrl); // await uploadResult 
        });

        app.post("/rest/imgUrl", (req, res)->{
            Img img = (Img) req.getBody(Img.class);
            db.sendImgDataToDb(img);
            res.send("imgUrl ok");
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