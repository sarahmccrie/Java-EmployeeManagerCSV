import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* Names: Ji Young Kim & Sarah McCrie
* Class: PROG24178 1231_18326, Winter 2023
* Assignment: Project
* Date: April 10th, 2023
* Program: Main.java
*/

//this is the file to launch the GUI
public class Main extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("EmployeeManagementGUI.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Employee Management System");
        primaryStage.show();
        
    }
} //end of Main class
