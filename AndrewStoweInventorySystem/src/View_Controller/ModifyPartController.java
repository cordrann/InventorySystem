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

public class ModifyPartController {
    //modify part text fields
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField inventory;
    @FXML private TextField cost;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField machineID_or_companyName;

    //modify part buttons
    @FXML private Button save;
    @FXML private Button cancel;

    //modify part radio buttons
    @FXML private RadioButton in_house;
    @FXML private RadioButton outsourced;

    //machine id/ company name label
    @FXML private Label machComp;

    //error label
    @FXML private Label errorLabel;

    /**
     *return to main screen
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
     *set text to machine id
     * @param event In house button toggled
     * @throws IOException
     */
    @FXML private void radioToggleInHouse(ActionEvent event) throws IOException {
        machComp.setText("Machine ID");
    }

    /**
     * set text to company name
     * @param event Outsourced button toggled
     * @throws IOException
     */
    @FXML private void radioToggleOutSourced(ActionEvent event) throws IOException{
        machComp.setText("Company Name");
    }
    //variable to track index of selected part
    int partIndex = -1;

    /**
     * transfers the data for the selected part into the modify part screen
     * @param selectedPart the part selected
     */
    public void transferPartData(Part selectedPart) {
        partIndex = Inventory.getAllParts().indexOf(selectedPart);
        id.setText(Integer.toString(selectedPart.getId()));
        name.setText(selectedPart.getName());
        inventory.setText(Integer.toString(selectedPart.getStock()));
        cost.setText(Double.toString(selectedPart.getPrice()));
        max.setText(Integer.toString(selectedPart.getMax()));
        min.setText(Integer.toString(selectedPart.getMin()));
        if(selectedPart instanceof InHousePart){
            in_house.setSelected(true);
            machComp.setText("Machine ID");
            machineID_or_companyName.setText(Integer.toString(((InHousePart) selectedPart).getMachineId()));
        }

        else{
            outsourced.setSelected(true);
            machComp.setText("Company Name");
            machineID_or_companyName.setText(((OutsourcedPart) selectedPart).getCompanyName());
        }
    }


    /**
     *attempt to save the updated part and return to main screen
     * @param event save button clicked
     * @throws IOException
     */
    @FXML private void saveClick(ActionEvent event) throws IOException {

        String newName = name.getText();
        int partID = Integer.parseInt(id.getText());

        //try/catch block makes sure data types are appropriate
        try {
            //parse user input and put into variables
            double newCost = Double.parseDouble(cost.getText());
            int newInventory = Integer.parseInt(inventory.getText());
            int newMin = Integer.parseInt(min.getText());
            int newMax = Integer.parseInt(max.getText());

            //checks for logical data errors from user input
            if (newMin >= 0 && newMax > 0 && newInventory >= newMin && newInventory <= newMax && newCost > 0.00 && newName.length() != 0) {

                //checks whether a part is in house or outsourced and updates that part
                if (in_house.isSelected()) {
                    int machineID = Integer.parseInt(machineID_or_companyName.getText());
                    Part updatedIHPart = new InHousePart(partID, newName, newCost, newInventory, newMin, newMax, machineID);
                    Inventory.updatePart(partIndex, updatedIHPart);
                } else {
                    String companyName = machineID_or_companyName.getText();
                    OutsourcedPart updatedOSPart = new OutsourcedPart(partID, newName, newCost, newInventory, newMin, newMax, companyName);
                    Inventory.updatePart(partIndex, updatedOSPart);
                }

                //return to main screen after updating part
                Parent modifyPartParent;
                modifyPartParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainUI.FXML")));
                Scene mainScene = new Scene(modifyPartParent);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(mainScene);
                app_stage.show();
            }

            //prints error messages for various user errors
            else if (newMin < 0) {
                errorLabel.setText("Minimum must be greater than or equal to 0");
            } else if (newMax <= 0) {
                errorLabel.setText("Maximum must be set to at least 1");
            } else if (newMax < newMin) {
                errorLabel.setText("Maximum must be greater than or equal to minimum");
            } else if (newInventory < newMin || newInventory > newMax) {
                errorLabel.setText("Inventory levels must be between minimum and maximum");
            } else if (newCost <= 0.00) {
                errorLabel.setText("Cost must be greater than 0");
            } else if (newName.length() == 0) {
                errorLabel.setText("The part must have a name");
            }
        } catch (Exception e) {
            errorLabel.setText("Invalid input, try again.");
        }
    }
}
