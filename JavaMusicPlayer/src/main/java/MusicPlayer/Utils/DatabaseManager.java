package MusicPlayer.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
//
//    // Méthode pour initialiser la base de données et créer la table songs si elle n'existe pas
//    public static void initializeDatabase() {
//        try {
//            // Connexion à la base de données
//            Connection connection = DBConnexion.getConnection();
//
//            // Vérification de l'existence de la table songs
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE 'songs'");
//
//            // Si la table songs n'existe pas, la créer
//            if (!resultSet.next()) {
//                statement.executeUpdate("CREATE TABLE IF NOT EXISTS songs ("
//                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
//                        + "title VARCHAR(255),"
//                        + "artist VARCHAR(255),"
//                        + "album VARCHAR(255),"
//                        + "genre VARCHAR(255)," // Ajout de la colonne 'genre'
//                        + "year INT,"
//                        + "song_path VARCHAR(255),"
//                        + "duration INT"
//                        + ")");
//
//                System.out.println("La table songs a été créée avec succès !");
//            } else {
//                System.out.println("La table songs existe déjà dans la base de données.");
//            }
//
//            // Fermer la connexion
//            connection.close();
//        } catch (SQLException e) {
//            System.err.println("Erreur lors de l'initialisation de la base de données : " + e.getMessage());
//        }
//    }
}
