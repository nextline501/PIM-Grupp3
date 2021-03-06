package Backend;

import java.sql.*;
import java.util.*;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import org.apache.commons.fileupload.FileItem;
import express.utils.Utils;

public class Database {
    private Connection conn;
    private int currentIdParam;

    public Database(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:PIM-Notes.db");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNote(Notes note){
        currentIdParam = note.getId();

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Notes (id, title, noteText) VALUES(?, ?, ?)");
            stmt.setInt(1, note.getId());
            stmt.setString(2, note.getTitle());
            stmt.setString(3, note.getNoteText());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public List<Notes> getNotes(){
        List<Notes> noteList = null;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM notes;");
            ResultSet rs = stmt.executeQuery();
            
            Notes[] itemsFromRS = (Notes[]) Utils.readResultSetToObject(rs, Notes[].class);
            noteList = List.of(itemsFromRS);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noteList;
    }

    public void deleteNote(Notes note){
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM notes WHERE notes.id = ?;");
            stmt.setInt(1, note.getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNotes(Notes note){
        currentIdParam = note.getId();

        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE notes SET noteText = ? WHERE notes.id = ?;");
            stmt.setString(1, note.getNoteText());
            stmt.setInt(2, note.getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String uploadImage(FileItem imageKek) {
        // the uploads folder in the "www" directory is accessible from the website
        // because the whole "www" folder gets served, with all its content

        // get filename with file.getName()
        String imageUrl = "/uploads/" + imageKek.getName(); //   ex.... "/uploads/LELE.png"

        // open an ObjectOutputStream with the path to the uploads folder in the "Frontend" directory
        try (var os = new FileOutputStream(Paths.get("src/Frontend" + imageUrl).toString())) {
            // get the required byte[] array to save to a file
            // with file.get()
            os.write(imageKek.get());

        } catch (Exception e) {
            e.printStackTrace();
            // if image is not saved, return null
            return null;
        }
        return imageUrl;
    }

    public void sendImgDataToDb(String url){
        Img img = new Img();
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO img (id, url, notes_id) VALUES (?, ?, ?)");
            stmt.setNull(1, img.getId());
            stmt.setString(2, url);
            stmt.setInt(3, currentIdParam);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UrlHolder> getImgUrl(int id){
        List<UrlHolder> urls = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT img.id, img.url FROM img WHERE img.notes_id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            
            UrlHolder[] itemsFromRS = (UrlHolder[]) Utils.readResultSetToObject(rs, UrlHolder[].class);
            urls = List.of(itemsFromRS);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }

    public void deleteSelectedImgById(int id){
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM img WHERE img.id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllImgBasedOnNoteId(int noteId){
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM img WHERE img.notes_id = ?");
            stmt.setInt(1, noteId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
