package hotel.mangement.system.hotel_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    Connection connection;
    Statement statement;

    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotel_management", "root", "mincraft");
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
