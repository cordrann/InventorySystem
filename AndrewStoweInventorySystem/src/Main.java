import View_Controller.MainUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *@author Andrew Stowe
 *  */

 /** In terms of potential future enhancements the most obvious would seem to be some kind of file i/o system to allow
 * permanent data storage. I would also slightly change the associated parts list to group multiple copies of the same
 * part into one listing on that table and add a # of copies column.
 *
 * A logical error I encountered while working on this project was when I was initially trying to modify parts based on their
 * part ids rather than their actual indexes in the allParts observable list. I corrected this by making sure to get the index
 * of the part and passing that as the first argument of the updatePart method rather than the part id.
 */
public class Main extends Application {

    /**
     *start the app and show the main screen
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View_Controller/MainUI.fxml"));
        primaryStage.setTitle("IMS");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();

    }




    public static void main(String[] args) {
        launch(args);
    }
}
