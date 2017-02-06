package chat.room;
import java.sql.*;

public class Database {
     Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    PreparedStatement prepsInsertProduct = null;
    String connectionString;
    
public void Database(){    

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
    
}
