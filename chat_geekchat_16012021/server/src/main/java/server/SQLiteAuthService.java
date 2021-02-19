package server;

import java.sql.*;

public class SQLiteAuthService {
    private static Connection connection;
    private static Statement statement;

    public static void connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:server/myUserList.db");
        statement = connection.createStatement();
    }


    //аутентификация
    public static String getNicknameByLoginAndPassword(String login, String password) {
        String str = ("select * from UserList where login = '" + login + "'");

        try {
            ResultSet resultSet = statement.executeQuery(str);
            if (resultSet.next()) {
                if (resultSet.getString("Password").equals(password))
                    return resultSet.getString("Nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //регистрация
    public static boolean registration(String login, String password, String nickname) {
        String check = ("select * from UserList where nickname = '" + nickname + "'");
        String add = String.format("insert into UserList (login, password, nickname) values ('%s', '%s', '%s')", login, password, nickname);

        try {
            ResultSet resultSetCheck = statement.executeQuery(check);
            if (resultSetCheck.next()) {
                if (resultSetCheck.getString("Nickname").equals(nickname))
                    return false;
            }
            ResultSet resultSetAdd = statement.executeQuery(add);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


    private static void print(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.print(resultSet.getString("Login") + " ");
            System.out.print(resultSet.getString("Password") + " ");
            System.out.print(resultSet.getString("Nickname") + "\n");
        }
    }

    //смена ника
    public static boolean changeNick(String oldNick, String login, String password, String nickname) {
        String check = ("select * from UserList where login = '" + login + "'");
        String change = String.format("update UserList set Nickname = '%s' where Login = '%s'", nickname, login);

        try {
            ResultSet resultSetCheck = statement.executeQuery(check);
            if (resultSetCheck.next()) {
                if (resultSetCheck.getString("password").equals(password)) {
                    ResultSet resultSetChange = statement.executeQuery(change);
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}


