package MusicPlayer.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class UpdateFormController {
    @FXML
    private TextField albumField, titleField, artistField, genreField, yearField;
    private Stage dialogStage;
    private boolean okClicked = false;
    @FXML
    private void initialize() {
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            okClicked = true;
            dialogStage.close();
        }
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    private boolean isInputValid() {
        String errorMessage = "";
        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "Titre invalide!\n";
        }
        if (artistField.getText() == null || artistField.getText().length() == 0) {
            errorMessage += "Artiste invalide!\n";
        }
        if (albumField.getText() == null || albumField.getText().length() == 0) {
            errorMessage += "Album invalide!\n";
        }
        if (genreField.getText() == null || genreField.getText().length() == 0) {
            errorMessage += "Genre invalide!\n";
        }
        if (yearField.getText() == null || yearField.getText().length() == 0) {
            errorMessage += "Année invalide!\n";
        } else {
            try {
                Integer.parseInt(yearField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Année invalide (doit être un entier)!\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
