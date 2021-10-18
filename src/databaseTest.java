import java.sql.*;

public class databaseTest {

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passworddb", "root", "bilikayo12");

        Statement statement = conn.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * from passwords");

        while (resultSet.next()){
            System.out.println(resultSet.getString("password"));
        }
    }
}
