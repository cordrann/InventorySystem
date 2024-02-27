package View_Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 *
 * @author Andrew Stowe
 */

public class AddProductController implements Initializable {
    //add product screen text fields
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField inventory;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;

    //part search bar for add product screen
    @FXML private TextField partSearch;

    //add product screen buttons
    @FXML private Button add;
    @FXML private Button removePart;
    @FXML private Button save;
    @FXML private Button cancel;

    //add product screen table views
    @FXML private TableView<Part> allPartsTable;
    @FXML private TableView<Part> productPartsTable;

    //all parts table columns
    @FXML private TableColumn<Part, Integer> allPartIDColumn;
    @FXML private TableColumn<Part, String> allPartNameColumn;
    @FXML private TableColumn<Part, Integer> allPartInventoryColumn;
    @FXML private TableColumn<Part, Double> allPartCostColumn;

    //product parts table columns
    @FXML private TableColumn<Part, Integer> productPartIDColumn;
    @FXML private TableColumn<Part, String> productPartNameColumn;
    @FXML private TableColumn<Part, Integer> productPartInventoryColumn;
    @FXML private TableColumn<Part, Double> productPartCostColumn;

    //add product screen error label
    @FXML private Label errorLabel;

    /**
     * Initialize the all parts table and filter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //populate the all parts table view
        if (Inventory.getAllParts().size() > 0) {
            allPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            allPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            allPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

            allPartsTable.setItems(Inventory.getAllParts());
        }
        //start the part filter
        partFilter();
    }

    //create a new list to store the parts selected for the product
    private ObservableList<Part> productParts = FXCollections.observableArrayList();

    /**
     *
     * @param event Click the add button moving the selected part to the associated parts list
     * @throws IOException
     */
    @FXML private void addClick(ActionEvent event) throws IOException{
        Part selectedPart = null;
        selectedPart = allPartsTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            errorLabel.setText("Please select a part to add");
        }

        else if (productParts.size() == 0){
            productParts.add(selectedPart);

            productPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            productPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            productPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            productPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

            productPartsTable.setItems(productParts);
        }

        else {
            productParts.add(selectedPart);
            productPartsTable.setItems(productParts);
        }
    }

    /**
     *
     * @param event Click the remove button, remove the selected part from the product
     * @throws IOException
     */
    @FXML private void removeClick(ActionEvent event) throws IOException{
        Part selectedPart = null;
        selectedPart = productPartsTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            errorLabel.setText("Please select a part to remove");
        }
        else {
            productParts.remove(selectedPart);

            productPartsTable.setItems(productParts);
        }
    }

    /**
     *
     * @param event click the cancel button to return to the main screen
     * @throws IOException
     */
    @FXML
    private void cancelCLick(ActionEvent event) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load((URL) Objects.requireNonNull(this.getClass().getResource("MainUI.FXML")));
        Scene addPartScene = new Scene(addPartParent);
        Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        app_stage.setScene(addPartScene);
        app_stage.show();
    }

    /**
     *
     * @param event click the save button to attempt to add the product to inventory and return to main screen
     * @throws IOException
     */
    @FXML private void saveClick(ActionEvent event) throws IOException {
        //generate a new id for the product
        int newId = MainUIController.productIDCounter;
        //get the name input
        String newName = name.getText();

        //try/catch block makes sure data types are appropriate
        try {
            //parse user input and put into variables
            double newPrice = Double.parseDouble(price.getText());
            int newInventory = Integer.parseInt(inventory.getText());
            int newMin = Integer.parseInt(min.getText());
            int newMax = Integer.parseInt(max.getText());


            //checks for logical data errors from user input
            if(newMin >= 0 && newMax > 0 && newInventory >= newMin && newInventory <= newMax && newPrice > 0.00 && newName.length() != 0) {

                //create the product
                Product newProduct = new Product(newId, newName, newPrice, newInventory, newMin, newMax);

                //iterates product id counter after adding a new product
                MainUIController.productIDCounter++;
                //add parts to product
                for (int i = 0; i < productParts.size();i++){
                    Part newAssociatePart = productParts.get(i);
                    newProduct.addAssociatedPart(newAssociatePart);
                }
                //add product to inventory
                Inventory.addProduct(newProduct);
                //return to main screen after adding product
                Parent addProductParent;
                addProductParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainUI.FXML")));
                Scene addProductScene = new Scene(addProductParent);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(addProductScene);
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
            else if (newPrice <= 0.00){
                errorLabel.setText("Price must be greater than 0");
            }
            else if (newName.length() == 0){
                errorLabel.setText("The product must have a name");
            }
        }
        catch(InputMismatchException e){
            errorLabel.setText("Invalid input, try again.");
        }
    }

    /**
     *
     * @param event text entered into the part filter text field
     */
    @FXML private void partFilterText(ActionEvent event){
        partFilter();
    }

    /**
     * Filters the parts based on input in the text field
     */
    private void partFilter(){
        partSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            //try catch to see if input is an ID(integer) or name search
            //try will execute if it is an integer
            try{
                int id = Integer.parseInt(newValue);
                Part filteredPart = Inventory.lookUpPart(id);
                ObservableList<Part> idFilteredList = FXCollections.observableArrayList();
                idFilteredList.add(filteredPart);
                allPartsTable.setItems(idFilteredList);
            }
            //catch will execute if it isn't
            catch(NumberFormatException e){
                SortedList<Part> sortedParts = new SortedList<>(Inventory.lookUpPart(newValue));
                sortedParts.comparatorProperty().bind(allPartsTable.comparatorProperty());
                allPartsTable.setItems(sortedParts);
            }

        });
    }
}
