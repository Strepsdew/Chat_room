package chat.room;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    public Profiili getEverythingById(int id) {
        Profiili k = null;
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
                k = new Profiili(id,username,etunimi,sukunimi,nickname,ika,bio,location,email);
            }
            
        }catch (Exception ex) {
            System.out.println("Error in selectProfiili : " + ex);
            return null;
        }
        return k;
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
                String query2 = "insert into kaverit (kaveri) values ('')";
                statement = connection.createStatement();
                
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
     
    public String getNicknameById(int id){
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select nickname from profile where ProfileID=?";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setInt(1, id);
            rs=prepsInsertProduct.executeQuery();
            if(rs.next()) {
                return rs.getString("nickname");
            }
        }catch (Exception ex){
            System.out.println("error in getNicknameById : "+ ex);
        }
        return null;
    }
    private JSONArray convertToJSON(ResultSet resultSet)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            for (int i = 0; i < total_rows; i++) {
                jsonArray.add(resultSet.getObject(i + 1));
            }
        }
        return jsonArray;
    }
    public JSONArray getFriendsById(int id){
        //muuta tämä silleen että returnaa JSONArrayn 
        //koska monta JSONObjectia  yhdessä JSONArrayssa on cleanimpi kuin yksin JSONobjecti
        JSONArray data = null; 
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select kaveri from kaverit where ProfileID=?";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setInt(1, id);   
            rs=prepsInsertProduct.executeQuery();
            data = convertToJSON(rs);
            System.out.println(data);
        }catch (Exception ex){
            System.out.println("error in getNicknameById : "+ ex);
        }
        return data;
    }
    public boolean addFriend(int currentUserId,int friendId){
        Database k = new Database();
        // tähän pitää tehdä lähetä pyyntö
        String kaverinimi = k.getNicknameById(friendId);
        JSONObject kaveri = new JSONObject();
        if (!kaverinimi.isEmpty()) return false;
        kaveri.put("nickname",kaverinimi);
        kaveri.put("ProfileID",friendId);
        JSONArray kaverilista =  k.getFriendsById(currentUserId);
        kaverilista.add(kaveri);
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "update kaverit set kaveri = ? where ProfileID = ?";
            prepsInsertProduct = connection.prepareStatement(query);
            String jsontostring = kaverilista.toString();
            prepsInsertProduct.setString(1,jsontostring);
            prepsInsertProduct.setInt(2,currentUserId);
            prepsInsertProduct.executeUpdate();
            return true;
        }catch (Exception ex) {
            System.out.println("Error in addFriend : "+ex);
            return false;
        }
    }
    public JSONObject getCurrentUserFriendById(int currentUserId,int FriendId) {
        Database k = new Database();
        JSONArray kaverilista = k.getFriendsById(currentUserId);
        System.out.println(kaverilista.get(0));
        if (kaverilista.contains(FriendId)) {
            int indeksi = kaverilista.indexOf(FriendId);
            JSONObject kaveri = (JSONObject) kaverilista.get(indeksi);
            return kaveri;
        }
        return null;
    }
    public static void main(String[] args) {
        Database k = new Database();
        k.addFriend(1,3);
        
    }
}
