package Backend;

import java.sql.*;

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
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Notes (id, NoteText, date) VALUES(?, ?, ?)");
            stmt.setNull(1, note.getId());
            stmt.setString(2, note.getNoteText());
            stmt.setString(3, note.getDate());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
