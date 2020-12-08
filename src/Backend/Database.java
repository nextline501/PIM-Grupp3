package Backend;

import java.sql.*;
import java.util.*;
import express.utils.Utils;

public class Database {
    private Connection conn;

    public Database(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:PIM-Notes.db");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNote(Notes note){
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Notes (id, noteText) VALUES(?, ?)");
            stmt.setNull(1, note.getId());
            stmt.setString(2, note.getNoteText());
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
}
