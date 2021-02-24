package server;

import java.sql.*;

public class SQLiteAuthService {

    public static void classForname() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
    }


    //аутентификация
    public static String getNicknameByLoginAndPassword(String login, String password) {
        String loginStr = ("select * from UserList where login = ? and password = ?");

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:server/myUserList.db")) {
            PreparedStatement psAuth = connection.prepareStatement(loginStr);
            psAuth.setString(1, login);
            psAuth.setString(2, password);
            ResultSet resultSet = psAuth.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("Nickname");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //регистрация
    public static boolean registration(String login, String password, String nickname) {
        String checkLogin = ("select * from UserList where Login = ?");
        String checkNick = ("select * from UserList where Nickname = ?");
        String reg = ("insert into UserList (Login, Password, Nickname) values (?, ?, ?)");

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:server/myUserList.db")) {
            PreparedStatement psCheckLogin = connection.prepareStatement(checkLogin);
            psCheckLogin.setString(1, login);
            ResultSet rsLoginCheck = psCheckLogin.executeQuery();

            if (rsLoginCheck.next()) {
                return false;
            } else {

                try (PreparedStatement psCheckNick = connection.prepareStatement(checkNick)) {
                    psCheckNick.setString(1, nickname);
                    ResultSet rsCheckNick = psCheckNick.executeQuery();

                    if (rsCheckNick.next()) {
                        return false;
                    } else {

                        try (PreparedStatement psRegistration = connection.prepareStatement(reg)) {
                            psRegistration.setString(1, login);
                            psRegistration.setString(2, password);
                            psRegistration.setString(3, nickname);
                            psRegistration.executeUpdate();
                            return true;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //смена ника
    public static boolean changeNick(String login, String password, String nickname) {
        String checkLogin = ("select * from UserList where Login = ? and password =?");
        String checkNick = ("select * from UserList where Nickname = ?");
        String change = ("update UserList set Nickname = ? where Login = ?");


        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:server/myUserList.db")) {
            PreparedStatement psCheck = connection.prepareStatement(checkLogin);
            psCheck.setString(1, login);
            psCheck.setString(2, password);
            ResultSet rsCheckLogin = psCheck.executeQuery();

            if (rsCheckLogin.next()) {

                try (PreparedStatement isNickAvailable = connection.prepareStatement(checkNick)) {
                    isNickAvailable.setString(1, nickname);
                    ResultSet rsCheckNick = isNickAvailable.executeQuery();

                    if (rsCheckNick.next()) {
                        return false;

                    } else {

                        try (PreparedStatement changeNick = connection.prepareStatement(change)) {
                            changeNick.setString(1, nickname);
                            changeNick.setString(2, login);
                            changeNick.executeUpdate();
                            return true;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}


