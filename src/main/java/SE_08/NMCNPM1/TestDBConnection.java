package SE_08.NMCNPM1;

import java.sql.*;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/thuphichungcu";
        String user = "root";
        String password = "";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) System.out.println("Kết nối thành công");

            String sql = "SELECT username FROM account";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int i = 0;
            while (resultSet.next()) {
                String data = resultSet.getString("username");
                System.out.println(++i + " " + data);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
