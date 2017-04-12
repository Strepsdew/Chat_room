package chat.room;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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

    public ArrayList<Profiili> getUserByNickname(String nimi) {
        ArrayList<Profiili> profiilit = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select * from profile where nickname=?";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setString(1, nimi);
            rs = prepsInsertProduct.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ProfileID");
                String username = rs.getString("username");
                String etunimi = rs.getString("etunimi");
                String sukunimi = rs.getString("sukunimi");
                String nickname = rs.getString("nickname");
                int ika = rs.getInt("ika");
                String bio = rs.getString("bio");
                String location = rs.getString("location");
                String email = rs.getString("email");
                Profiili k = new Profiili(id, username, etunimi, sukunimi, nickname, ika, bio, location, email);
                profiilit.add(k);
            }

        } catch (Exception ex) {
            System.out.println("Error in getUserByUsername : " + ex);
            return null;
        }
        return profiilit;
    }

    public int getIdByNickname(String nickname) {
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select ProfileID from profile where nickname=?";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setString(1, nickname);
            rs = prepsInsertProduct.executeQuery();
            if (rs.next()) {
                return rs.getInt("ProfileID");
            }
        } catch (Exception ex) {
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
            rs = prepsInsertProduct.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("ProfileID");
                String username = rs.getString("username");
                String etunimi = rs.getString("etunimi");
                String sukunimi = rs.getString("sukunimi");
                String nickname = rs.getString("nickname");
                int ika = rs.getInt("ika");
                String bio = rs.getString("bio");
                String location = rs.getString("location");
                String email = rs.getString("email");
                k = new Profiili(id, username, etunimi, sukunimi, nickname, ika, bio, location, email);
            }

        } catch (Exception ex) {
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
            rs = prepsInsertProduct.executeQuery();
            if (rs.next()) {
                Object jotain = rs.getObject("password");
                return jotain;
            }
        } catch (Exception ex) {
            System.out.println("error in getHashPasswordByNickname : " + ex);
            return null;
        }
        return null;
    }

    public boolean createUser(String Username, String etunimi, String sukunimi, String nickname, String password) {
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
                prepsInsertProduct = connection.prepareStatement(query2);
                prepsInsertProduct.executeUpdate();
            } catch (Exception ex) {
                System.out.println("Error in createUser : " + ex);
            }
            return true;
        }
        return false;
    }

    public boolean checkPassword(String nickname, String pw) {
        Object hash = getHashPasswordByNickname(nickname);
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select PWDCOMPARE (?,?)";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setString(1, pw);
            prepsInsertProduct.setObject(2, hash);
            rs = prepsInsertProduct.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) == 1) {
                    return true;
                }
            }
        } catch (Exception ex) {
            System.out.println("error in checkPassword : " + ex);
            return false;
        }
        return false;
    }

    public void updateProfiili(int id, String nickname, int ika, String bio, String location) {
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "Update profile set nickname=? ika=? bio=? location=? where ProfiiliID=?";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setString(1, nickname);
            prepsInsertProduct.setInt(2, ika);
            prepsInsertProduct.setString(3, bio);
            prepsInsertProduct.setString(4, location);
            prepsInsertProduct.setInt(5, id);
            prepsInsertProduct.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error in updateProfiili : " + ex);
        }
    }

    public String getNicknameById(int id) {
        try {
            connection = DriverManager.getConnection(connectionString);
            String query = "select nickname from profile where ProfileID=?";
            prepsInsertProduct = connection.prepareStatement(query);
            prepsInsertProduct.setInt(1, id);
            rs = prepsInsertProduct.executeQuery();
            if (rs.next()) {
                return rs.getString("nickname");
            }
        } catch (Exception ex) {
            System.out.println("error in getNicknameById : " + ex);
        }
        return null;
    }

    public JsonObject getFriendsByIdInJsonObject(int id) {
        try {
            connection = DriverManager.getConnection(connectionString);
            String sql = "select kaveri from kaverit where ProfileID=?";
            prepsInsertProduct = connection.prepareStatement(sql);
            prepsInsertProduct.setInt(1, id);
            rs = prepsInsertProduct.executeQuery();
            String kaverit = "";
            if (rs.next()) {
                kaverit = rs.getString("kaveri");
            }
            if (kaverit.isEmpty()) {
                return null;
            }
            JsonParser jsonparser = new JsonParser();
            JsonObject obj = (JsonObject) jsonparser.parse(kaverit);

            return obj;
        } catch (Exception ex) {
            System.out.println("error in getfriendsbyid " + ex);
            return null;
        }
    }

    public Kaveri getFriendsByIdInKaveri(int id) {
        Kaveri kaveri = new Kaveri();
        try {
            connection = DriverManager.getConnection(connectionString);
            String sql = "select kaveri from kaverit where ProfileID=?";
            prepsInsertProduct = connection.prepareStatement(sql);
            prepsInsertProduct.setInt(1, id);
            rs = prepsInsertProduct.executeQuery();
            String kaverit = "";
            if (rs.next()) {
                kaverit = rs.getString("kaveri");
            }
            if (kaverit.isEmpty()) {
                return null;
            }
            JsonParser jsonparser = new JsonParser();
            JsonObject obj = (JsonObject) jsonparser.parse(kaverit);
            JsonArray arrayofnames = obj.get("friendnames").getAsJsonArray();
            JsonArray arrayofids = obj.get("IDs").getAsJsonArray();
            int i = 0;
            for (JsonElement arrayofid : arrayofids) {
                kaveri.addFriend(arrayofnames.get(i).getAsString(), arrayofid.getAsInt());
                i++;
            }
            return kaveri;
        } catch (Exception ex) {
            System.out.println("error in getfriendsbyid " + ex);
            return null;
        }
    }

    public boolean haveThisFriend(String kaverinimi, int friendId, int currentUserId) {
        Database d = new Database();
        Kaveri kaveri = null;
        if (d.getFriendsByIdInKaveri(currentUserId) != null) {
            kaveri = d.getFriendsByIdInKaveri(currentUserId);
        } else {
            kaveri = new Kaveri();
        }
        if (Objects.nonNull(kaveri.getIds())) {
            for (int id : kaveri.getIds()) {
                if (id == friendId) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean addFriend(int currentUserId, int friendId) {
        Database d = new Database();
        Gson gson = new Gson();
        JsonParser jsonparser = new JsonParser();

        String kaverinimi = d.getNicknameById(friendId);
        Kaveri kaverit = null;
        if (d.getFriendsByIdInKaveri(currentUserId) != null) {
            kaverit = d.getFriendsByIdInKaveri(currentUserId);
        } else {
            kaverit = new Kaveri();
        }
        if (!haveThisFriend(kaverinimi, friendId, currentUserId)) {
            kaverit.addFriend(kaverinimi, friendId);
        } else {
            return false;
        }

        String kaveritstring = gson.toJson(kaverit);
        JsonObject obj = (JsonObject) jsonparser.parse(kaveritstring);
        String insert = obj.toString();

        String currentUser = d.getNicknameById(currentUserId);
        Kaveri kaverinkaverit = null;
        if (d.getFriendsByIdInKaveri(friendId) != null) {
            kaverinkaverit = d.getFriendsByIdInKaveri(friendId);
        } else {
            kaverinkaverit = new Kaveri();
        }
        if (!haveThisFriend(currentUser, currentUserId, friendId)) {
            kaverinkaverit.addFriend(currentUser, currentUserId);
        } else {
            return false;
        }

        String kaverinkaveritstring = gson.toJson(kaverinkaverit);
        JsonObject obj2 = (JsonObject) jsonparser.parse(kaverinkaveritstring);
        String insert2 = obj2.toString();

        try {
            connection = DriverManager.getConnection(connectionString);
            String sql = "update kaverit set kaveri =? where ProfileID =?";
            prepsInsertProduct = connection.prepareStatement(sql);
            prepsInsertProduct.setString(1, insert);
            prepsInsertProduct.setInt(2, currentUserId);
            prepsInsertProduct.execute();

            String sql2 = "update kaverit set kaveri =? where ProfileID =?";
            prepsInsertProduct = connection.prepareStatement(sql2);
            prepsInsertProduct.setString(1, insert2);
            prepsInsertProduct.setInt(2, friendId);
            prepsInsertProduct.execute();

            return true;
        } catch (Exception ex) {
            System.out.println("Error in addFriend" + ex);
        }
        return false;
    }

    public HashMap<String, Integer> getFriendByNickname(int currentUserId, String nickname) {
        HashMap<String, Integer> kaveri = new HashMap<>();
        JsonParser jsonparser = new JsonParser();
        try {
            connection = DriverManager.getConnection(connectionString);
            String sql = "SELECT * FROM kaverit where ProfileID=?";
            prepsInsertProduct = connection.prepareStatement(sql);
            prepsInsertProduct.setInt(1, currentUserId);
            rs = prepsInsertProduct.executeQuery();
            String kaverit = "";
            if (rs.next()) {
                kaverit = rs.getString("kaveri");
            }
            if (kaverit.isEmpty()) {
                return null;
            }
            JsonObject obj = (JsonObject) jsonparser.parse(kaverit);
            JsonArray arrayofids = obj.get("IDs").getAsJsonArray();
            JsonArray arrayofnames = obj.get("friendnames").getAsJsonArray();
            int i = 0;
            for (JsonElement name : arrayofnames) {
                String nimi = name.getAsString();
                if (nimi.equals(nickname)) {
                    kaveri.put(nimi, arrayofids.get(i).getAsInt());
                }
                i++;
            }
            return kaveri;
        } catch (Exception ex) {
            System.out.println("Error in getFriendByNickname" + ex);
            return null;
        }
    }

    public boolean removeFriendById(int currentUserId, int removeThis) {
        Database d = new Database();
        Gson gson = new Gson();
        JsonParser jsonparser = new JsonParser();
        JsonObject obj = d.getFriendsByIdInJsonObject(currentUserId);
        final JsonArray pituus = obj.getAsJsonArray("friendnames");
        for (int i = 0; i < pituus.size(); i++) {
            if (obj.getAsJsonArray("IDs").get(i).getAsInt() == removeThis) {
                obj.getAsJsonArray("IDs").remove(i);
                obj.getAsJsonArray("friendnames").remove(i);
                i--;
            }
        }
        String kaverit = obj.toString();
        try {
            connection = DriverManager.getConnection(connectionString);
            String sql = "update kaverit set kaveri =? where ProfileID=?";
            prepsInsertProduct = connection.prepareStatement(sql);
            prepsInsertProduct.setString(1, kaverit);
            prepsInsertProduct.setInt(2, currentUserId);
            prepsInsertProduct.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Error in removeFriendById  : " + ex);
            return false;
        }
    }

    public boolean insertPicture(File file, int currentUserId) {
        try {
            BufferedImage img = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            Blob blFile = new javax.sql.rowset.serial.SerialBlob(baos.toByteArray());
            connection = DriverManager.getConnection(connectionString);
            String sql = "update profile set ProfilePhoto=? where ProfileID=?";
            prepsInsertProduct = connection.prepareStatement(sql);
            prepsInsertProduct.setBlob(1, blFile);
            prepsInsertProduct.setInt(2, currentUserId);
            prepsInsertProduct.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Error in insertPicture : " + ex);
            return false;
        }
    }

    public Blob getPicture(int id) {
        Blob result = null;
        try {
            connection = DriverManager.getConnection(connectionString);
            String sql = "select ProfilePhoto from profile where ProfileID=?";
            prepsInsertProduct = connection.prepareStatement(sql);
            prepsInsertProduct.setInt(1, id);
            rs = prepsInsertProduct.executeQuery();
            if (rs.next()) {
                result = rs.getBlob("ProfilePhoto");
            }
            System.out.println(result);
            return result;
        } catch (Exception ex) {
            System.out.println("Error in getPicture : " + ex);
            return null;
        }
    }

    public BufferedImage getBufferedImageById(int id) throws IOException, SQLException {
        Database k = new Database();
        Blob j = k.getPicture(id);
        if (Objects.nonNull(j)) {
            InputStream in = j.getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            return image;
        }
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
        Database meme = new Database();
    }
}
