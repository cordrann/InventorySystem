package View_Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
/**
 *
 * @author Andrew Stowe
 */

public class AddPartController {
    //add part text fields
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField inventory;
    @FXML private TextField cost;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField machineID_or_companyName;

    //add part buttons
    @FXML private Button save;
    @FXML private Button cancel;

    //add part radio buttons
    @FXML private RadioButton in_house;
    @FXML private RadioButton outsourced;

    //Machine ID/ Company Name label
    @FXML private Label machComp;

    //error label
    @FXML private Label errorLabel;

    /**
     *returns to main screen
     * @param event cancel button clicked
     * @throws IOException
     */
    @FXML private void cancelCLick(ActionEvent event) throws IOException {
        Parent addPartParent;
        addPartParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainUI.FXML")));
        Scene addPartScene = new Scene(addPartParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(addPartScene);
        app_stage.show();

    }

    /**
     * sets label text
     * @param event In house part toggled
     * @throws IOException
     */
    @FXML private void radioToggleInHouse(ActionEvent event) throws IOException {
        machComp.setText("Machine ID");
    }

    /**
     *sets label text
     * @param event Outsourced part toggled
     * @throws IOException
     */
    @FXML private void radioToggleOutSourced(ActionEvent event) throws IOException{
        machComp.setText("Company Name");
    }

    /**
     *attempt to save the part and then return to main screen
     * @param event save button clicked
     * @throws IOException
     */
    @FXML private void saveClick(ActionEvent event) throws IOException{
        //generates a new id for the part
        int newId = MainUIController.partIDCounter;
        String newName = name.getText();

        //try/catch block makes sure data types are appropriate
        try {
            //parse user input and put into variables
            double newCost = Double.parseDouble(cost.getText());
            int newInventory = Integer.parseInt(inventory.getText());
            int newMin = Integer.parseInt(min.getText());
            int newMax = Integer.parseInt(max.getText());

            //checks for logical data errors from user input
            if(newMin >= 0 && newMax > 0 && newInventory >= newMin && newInventory <= newMax && newCost > 0.00 && newName.length() != 0) {

                //checks whether a part is in house or outsourced and creates a part of the appropriate type
                if (in_house.isSelected()) {
                    int machineID = Integer.parseInt(machineID_or_companyName.getText());
                    InHousePart newIHPart = new InHousePart(newId, newName, newCost, newInventory, newMin, newMax, machineID);
                    Inventory.addPart(newIHPart);
                }
                else {
                    String companyName = machineID_or_companyName.getText();
                    OutsourcedPart newOSPart = new OutsourcedPart(newId, newName, newCost, newInventory, newMin, newMax, companyName);
                    Inventory.addPart(newOSPart);
                }
                //iterates part id counter after adding a new part
                MainUIController.partIDCounter++;

                //return to main screen after adding part
                Parent addPartParent;
                addPartParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainUI.FXML")));
                Scene addPartScene = new Scene(addPartParent);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(addPartScene);
                app_stage.show();
            }

            //prints error messages for various user errors
            else if (newMin < 0){
                errorLabel.setText("Minimum must be greater than or equal to 0");
            }
            else if (newMax <= 0){
                errorLabel.setText("Maximum must be set to at least 1");
            }
            else if (newMax < newMin){
                errorLabel.setText("Maximum must be great than minimum");
            }
            else if (newInventory < newMin || newInventory > newMax){
                errorLabel.setText("Inventory levels must be between minimum and maximum");
            }
            else if (newCost <= 0.00){
                errorLabel.setText("Cost must be greater than 0");
            }
            else if (newName.length() == 0){
                errorLabel.setText("The part must have a name");
            }
        }
        catch(Exception e){
            errorLabel.setText("Invalid input, try again.");
        }







    }
}
