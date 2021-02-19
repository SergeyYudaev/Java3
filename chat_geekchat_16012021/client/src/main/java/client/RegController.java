package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegController {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField nicknameField;
    @FXML
    public TextArea textArea;
    public Button backButton;
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void tryToReg(ActionEvent actionEvent) {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();
        String nickname = nicknameField.getText().trim();

        controller.tryToReg(login, password, nickname);
    }

    public void regOk(){
        textArea.appendText("Регистрация прошла успешно\n");
    }

    public void regNo(){
        textArea.appendText("Логин или ник уже заняты\n");
    }

    public void changeNickOk(){
        textArea.appendText("Ник успешно сменен\n");
    }

    public void changeNickNo(){
        textArea.appendText("Ник сменить не удалось\n");
    }

    //в общем, если кликнуть кнупку "Chng nick", то логин, пароль, и новый ник полетят отсюда в Controller, из него
    //в ClientHandler, он уже вызовет статический метод из SQLiteAuthService, который найдет строку с таким ником,
    //проверит у нее пароль, и если тот совпадет, заменит ник из базы на тот, что введен в поле.
    public void changeNick(ActionEvent actionEvent) {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();
        String newNick = nicknameField.getText().trim();
        controller.tryToChangeNick(login, password, newNick);
    }

    public void Back(ActionEvent actionEvent) {
        Stage stage = (Stage)backButton.getScene().getWindow();
        stage.close();
    }
}
