package Backend;

import java.nio.file.Paths;
import java.util.Iterator;
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
        //getting imgData from Database
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
        //Deleting note and all images based on noteId
        app.post("/rest/delete", (req, res) ->{
            Notes note = (Notes) req.getBody(Notes.class);
            db.deleteNote(note);
            db.deleteAllImgBasedOnNoteId(note.getId());
            res.send("delete post ok");
        });

        app.post("/rest/update", (req, res)->{
            Notes note = (Notes) req.getBody(Notes.class);
            db.updateNotes(note);
            res.send("update ok");
        });
        //Sending imageData to Database
        app.post("/api/file-upload", (req, res)->{
            String imgUrl = null;
            try {
                List<FileItem> imgFilesList = req.getFormData("filesKek");

                Iterator<FileItem> it = imgFilesList.iterator();
                int index = 0;

                while(it.hasNext()){
                    if(index >= imgFilesList.size()){
                        index = 0;
                        break;
                    }
                    imgUrl = db.uploadImage(imgFilesList.get(index));
                    db.sendImgDataToDb(imgUrl);
                    index++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            res.send("post ok"); // await uploadResult 
        });

        //Deleting images and files separately based on imgId
        app.post("/rest/imgDelete", (req, res)->{
            UrlHolder urlobj = (UrlHolder) req.getBody(UrlHolder.class);
            db.deleteSelectedImgById(urlobj.getId());//UrlHolderId
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