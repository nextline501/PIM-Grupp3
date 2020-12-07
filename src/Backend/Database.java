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

    


}
