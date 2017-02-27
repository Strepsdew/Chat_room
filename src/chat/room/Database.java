package chat.room;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement prepsInsertProduct = null;
    String connectionString;

    public Database() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            connectionString
                    = "jdbc:sqlserver://chatservu.database.windows.net:1433;"
                    + "database=Chat_Room;"
                    + "user=Ohjelma@chatservu;"
                    + "password=Tämäonkiva1;"
                    + "encrypt=true;"
                    + "trustServerCertificate=false;"
                    + "hostNameInCertificate=*.database.windows.net;"
                    + "loginTimeout=30;";

        } catch (Exception ex) {
            System.out.println("error in connecting database" + ex);
        }
    }

  

    public ArrayList<Profiili>selectProfiiliByUsername(String nimi) {
        ArrayList<Profiili> profiilit = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select * from chat_room where username=?";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setString(1, nimi);
            rs=prepsInsertProduct.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("ProfileID");
                String username = rs.getString("username");
                String etunimi = rs.getString("etunimi");
                String sukunimi = rs.getString("sukunimi");
                String nickname = rs.getString("nickname");
                int ika = rs.getInt("ika");
                String bio = rs.getString("bio");
                String location = rs.getString("location");
                Profiili k = new Profiili(id,username,etunimi,sukunimi,nickname,ika,bio,location);
                profiilit.add(k);
            }
            
        }catch (Exception ex) {
            System.out.println("Error in selectProfiili : " + ex);
            return null;
        }
        return profiilit;
    }
    public static void main(String[] args) {
        Database k = new Database();
        ArrayList<Profiili> l = new ArrayList<>();
        l = k.selectProfiiliByUsername("fereal");
        System.out.println(l);
        
    }
}
