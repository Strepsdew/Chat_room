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
            String query = "select * from Profile where username=?";
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
            System.out.println("Error in selectProfiili : " + ex);
            return null;
        }
        return profiilit;
    }
    public boolean createUser(String Username,String etunimi,String sukunimi, String nickname,String password) {
        ArrayList<Profiili> k = new ArrayList<>();
        k = selectProfiiliByUsername(Username);
        System.out.println(k);
        if (k.size() == 0) {
            System.out.println("you is in");
            try {
                connection = DriverManager.getConnection(connectionString);
                String query = "INSERT INTO Profile (username,etunimi,sukunimi,nickname,password) values (?,?,?,?,?)";
                prepsInsertProduct = connection.prepareStatement(query);
                prepsInsertProduct.setString(1, Username);
                prepsInsertProduct.setString(2, etunimi);
                prepsInsertProduct.setString(3, sukunimi);
                prepsInsertProduct.setString(4, nickname);
                password = md5(password);
                prepsInsertProduct.setString(5, password);
                prepsInsertProduct.executeUpdate();
            } catch (Exception ex) {
                System.out.println("Error in createUser : " + ex);
            }
            return true;
        }
     return false;
    }
    public void updateProfiili(int id,String nickname,int ika, String bio,String location) {
       try{
           connection = DriverManager.getConnection(connectionString);
           String query ="Update Profile set nickname=? ika=? bio=? location=? where ProfiiliID=?";
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
        ArrayList<Profiili> l = new ArrayList<>();
        k.createUser("peke", "peke","peke","peke", "peke");
    }
            public static String md5(String input) {

        String md5 = null;

        if (null == input) {
            return null;
        }

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex) 
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }
}
