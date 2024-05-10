package MusicPlayer.Controller;

import MusicPlayer.Main;
import MusicPlayer.Utils.DBConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomeController {

    private Main main;
    @FXML
    private ListView<String> songListView;
    private ObservableList<String> songList = FXCollections.observableArrayList();

    @FXML
    protected void initialize() {
        // Appeler la méthode pour charger les chansons au démarrage de la vue
        loadSongsFromDatabase();
    }


    public void loadSongsFromDatabase() {
        try {
            // Connexion à la base de données
            Connection connection = DBConnexion.getConnection();

            // Requête pour sélectionner tous les titres et les IDs des chansons de la base de données
            String query = "SELECT id, title FROM songs";

            // Exécution de la requête
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Effacer la liste des chansons existantes
            songList.clear();

            // Ajout des titres des chansons à la liste observable
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                songList.add(id + " - " + title);
            }

            // Fermeture des ressources
            resultSet.close();
            statement.close();
            DBConnexion.closeConnection();

            // Mise à jour de la ListView avec la liste des chansons
            songListView.setItems(songList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void confirmDeleteButtonClicked() {
        String selectedSong = this.getSelectedSong();
        if (selectedSong != null && !selectedSong.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment supprimer cette chanson ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    Connection connection = DBConnexion.getConnection();
                    if (connection == null) {
                        System.out.println("La connexion à la base de données a échoué.");
                        return;
                    }

                    String deleteQuery = "DELETE FROM songs WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                    preparedStatement.setString(1, selectedSong);
                    int rowsAffected = preparedStatement.executeUpdate();

                    preparedStatement.close();
                    connection.close();

                    System.out.println("Nombre de lignes affectées : " + rowsAffected);

                    if (rowsAffected > 0) {
                        this.loadSongsFromDatabase();
                        System.out.println("Chanson supprimée avec succès de la base de données.");
                    } else {
                        System.out.println("La chanson n'a pas été trouvée dans la base de données.");

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Veuillez sélectionner une chanson à supprimer.");
            errorAlert.showAndWait();
        }
    }
    @FXML
    protected void likeButtonClicked() {
        String selectedSong = this.getSelectedSong();
        if (selectedSong != null && !selectedSong.isEmpty()) {
            try {
                Connection connection = DBConnexion.getConnection();
                if (connection == null) {
                    System.out.println("La connexion à la base de données a échoué.");
                    return;
                }

                // Exécuter une requête de mise à jour pour définir isFavorite sur true pour la chanson sélectionnée
                String updateQuery = "UPDATE songs SET isFavorite = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, Integer.parseInt(selectedSong.split(" - ")[0])); // Récupérer l'ID de la chanson
                int rowsAffected = preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();

                System.out.println("Nombre de lignes affectées : " + rowsAffected);

                if (rowsAffected > 0) {
                    System.out.println("Chanson marquée comme favorite avec succès.");
                } else {
                    System.out.println("La chanson n'a pas été trouvée dans la base de données.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Veuillez sélectionner une chanson pour la marquer comme favorite.");
            errorAlert.showAndWait();
        }
    }

    public String getSelectedSong() {
        return songListView.getSelectionModel().getSelectedItem();
    }

    @FXML
    protected void OpenPlaylists() {
    System.out.println("Open Playlist !");
    }

    @FXML
    protected void PlaySong() {
    Main.getInstance().loadView("player-view.fxml");
        }


}
