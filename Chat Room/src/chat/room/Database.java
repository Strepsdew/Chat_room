package chat.room;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                    + "user=Strepsdew@chatservu;"
                    + "password=Tämäonkiva1;"
                    + "encrypt=true;"
                    + "trustServerCertificate=false;"
                    + "hostNameInCertificate=*.database.windows.net;"
                    + "loginTimeout=30;";

        } catch (Exception ex) {
            System.out.println("error in connecting database" + ex);
        }
    }

  

    public ArrayList<Profiili>getUserByNickname(String nimi) {
        ArrayList<Profiili> profiilit = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select * from profile where nickname=?";
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
                String email = rs.getString("email");
                Profiili k = new Profiili(id,username,etunimi,sukunimi,nickname,ika,bio,location,email);
                profiilit.add(k);
            }
            
        }catch (Exception ex) {
            System.out.println("Error in getUserByUsername : " + ex);
            return null;
        }
        return profiilit;
    }
    public int getIdByNickname(String nickname){
        try{
            connection = DriverManager.getConnection(connectionString);
            String query = "select ProfileID from profile where nickname=?";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setString(1,nickname);
            rs=prepsInsertProduct.executeQuery();
            if(rs.next()){
               return rs.getInt("ProfileID");
            }
        }catch (Exception ex) {
            System.out.println("Error in getIdByNickname : " + ex);
        }
        return 0;
    }
    public ArrayList<Profiili> getUserById(int id) {
        ArrayList<Profiili> profiilit = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select * from profile where ProfileID=?";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setInt(1, id);
            rs=prepsInsertProduct.executeQuery();
            while(rs.next()) {
                int ID = rs.getInt("ProfileID");
                String username = rs.getString("username");
                String etunimi = rs.getString("etunimi");
                String sukunimi = rs.getString("sukunimi");
                String nickname = rs.getString("nickname");
                int ika = rs.getInt("ika");
                String bio = rs.getString("bio");
                String location = rs.getString("location");
                String email = rs.getString("email");
                Profiili k = new Profiili(id,username,etunimi,sukunimi,nickname,ika,bio,location,email);
                profiilit.add(k);
            }
            
        }catch (Exception ex) {
            System.out.println("Error in selectProfiili : " + ex);
            return null;
        }
        return profiilit;
    }
    public Object getHashPasswordByNickname(String username) {
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select password from profile where nickname=?";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setString(1, username);
            rs=prepsInsertProduct.executeQuery();
            if(rs.next()) {
            Object jotain = rs.getObject("password");
            return jotain;
            }
        }catch (Exception ex){
            System.out.println("error in getHashPasswordByNickname : "+ ex);
            return null;
        }
        return null;
    }
    public boolean createUser(String Username,String etunimi,String sukunimi, String nickname,String password) {
        ArrayList<Profiili> k = new ArrayList<>();
        k = getUserByNickname(nickname);
        System.out.println(k);
        if (k.size() == 0) {
            try {
                connection = DriverManager.getConnection(connectionString);
                String query = "INSERT INTO profile (username,etunimi,sukunimi,nickname,password) values (?,?,?,?,PWDENCRYPT(?))";
                prepsInsertProduct = connection.prepareStatement(query);
                prepsInsertProduct.setString(1, Username);
                prepsInsertProduct.setString(2, etunimi);
                prepsInsertProduct.setString(3, sukunimi);
                prepsInsertProduct.setString(4, nickname);
                prepsInsertProduct.setString(5, password);
                prepsInsertProduct.executeUpdate();
            } catch (Exception ex) {
                System.out.println("Error in createUser : " + ex);
            }
            return true;
        }
     return false;
    }
    public boolean checkPassword(String nickname,String pw) {
        Object hash = getHashPasswordByNickname(nickname);
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select PWDCOMPARE (?,?)";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setString(1, pw);
            prepsInsertProduct.setObject(2, hash);
            rs=prepsInsertProduct.executeQuery();
            if (rs.next()) {
                if(rs.getInt(1)==1){
                    return true;
                }
            }
        }catch (Exception ex){
            System.out.println("error in checkPassword : "+ ex);
            return false;
        }
        return false;
    }
    public void updateProfiili(int id,String nickname,int ika, String bio,String location) {
       try{
           connection = DriverManager.getConnection(connectionString);
           String query ="Update profile set nickname=? ika=? bio=? location=? where ProfiiliID=?";
           prepsInsertProduct = connection.prepareStatement(query);
           prepsInsertProduct.setString(1, nickname);
           prepsInsertProduct.setInt(2,ika);
           prepsInsertProduct.setString(3, bio);
           prepsInsertProduct.setString(4, location);
           prepsInsertProduct.setInt(5, id);           
           prepsInsertProduct.executeUpdate();
       }catch (Exception ex) {
           System.out.println("Error in updateProfiili : " +ex);
       }
    }
        
    public static void main(String[] args) {
        Database k = new Database();
    }        
}
