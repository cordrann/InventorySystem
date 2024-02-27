package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.*;

/**
 *
 * @author Andrew Stowe
 */

public class MainUIController implements Initializable {

    //Main menu search fields
    @FXML private TextField partSearch;
    @FXML private TextField productSearch;

    //table and columns for parts table
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partCostColumn;

    //table and columns for products table
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productCostColumn;


    //Main menu part buttons
    @FXML private Button addPart;
    @FXML private Button modifyPart;
    @FXML private Button deletePart;

    //Main menu product buttons
    @FXML private Button addProduct;
    @FXML private Button modifyProduct;
    @FXML private Button deleteProduct;

    //exit button
    @FXML private Button exitButton;

    //label for error dialog
    @FXML private Label errorLabel;

    //Method called for add part button press

    /**
     *Shows the add part screen
     * @param event clicking the add part button
     * @throws IOException
     */
    @FXML private void addPartClick(ActionEvent event) throws IOException{
        Parent addPartParent;
        addPartParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPartUI.FXML")));
        Scene addPartScene = new Scene(addPartParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(addPartScene);
        app_stage.show();

    }


    /**
     *passes data and shows the modify part screen
     * @param event clicking the modify part button
     * @throws IOException
     */
    @FXML private void modifyPartClick(ActionEvent event) throws IOException{
        //get the data for the selected part
        Part selectedPart = null;
        selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        //make sure a part was selected
        if(selectedPart == null){
            errorLabel.setText("Please select a part to modify");
        }

        else {
            //load the modify part screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPartUI.FXML"));
            Parent modifyPartParent = loader.load();

            //transfer part data from main screen to modify screen
            ModifyPartController mpc = loader.getController();
            mpc.transferPartData(selectedPart);

            //show the modify part screen
            Scene modifyPartScene = new Scene(modifyPartParent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(modifyPartScene);
            app_stage.show();
        }
    }

    //Alert pop up to confirm various actions
    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     *Attempts to delete the selected part
     * @param event clicking the delete part button
     * @throws IOException
     */
    @FXML private void deletePartClick(ActionEvent event) throws IOException{

        //get the data for the selected part
        Part selectedPart = null;
        selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        //make sure a part was selected
        if(selectedPart == null){
            errorLabel.setText("Please select a part to delete");
        }

        else {
            //pop up to confirm delete
            confirmation.setHeaderText("Are you sure you would like to delete this part?");
            confirmation.setContentText(selectedPart.getName());
            Optional<ButtonType> option = confirmation.showAndWait();

            //make sure an option is selected from pop up
            if (option.get() == null) {
                errorLabel.setText("Please select a button to proceed");
            }

            //if user clicks ok attempt to delete part and report success or failure
            else if (option.get() == ButtonType.OK) {
                boolean deleted = Inventory.deletePart(selectedPart);
                if(deleted) {
                    errorLabel.setText("Part deleted");
                }
                else{
                    errorLabel.setText("Part deletion failed, part not found");
                }
            }

            //if user clicks cancel tell user deletion was cancelled
            else {
                errorLabel.setText("Part deletion cancelled");
            }
        }
    }


    /**
     *shows add product screen
     * @param event clicking the add product button
     * @throws IOException
     */
    @FXML private void addProductClick(ActionEvent event) throws IOException{
        Parent addPartParent;
        addPartParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProductUI.FXML")));
        Scene addPartScene = new Scene(addPartParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(addPartScene);
        app_stage.show();

    }

    /**
     *passes data and shows modify product screen
     * @param event clicking the modify product button
     * @throws IOException
     */
    @FXML private void modifyProductClick(ActionEvent event) throws IOException{
        //get the data for the selected product
        Product selectedProduct = null;
        selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        //make sure a part was selected
        if(selectedProduct == null){
            errorLabel.setText("Please select a product to modify");
        }

        else {
            //load the modify product screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProductUI.FXML"));
            Parent modifyProductParent = loader.load();

            //transfer product data from main screen to modify screen
            ModifyProductController mpc = loader.getController();
            mpc.transferProductData(selectedProduct);

            //show the modify product screen
            Scene modifyProductScene = new Scene(modifyProductParent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(modifyProductScene);
            app_stage.show();
        }

    }

    /**
     *Attempt to delete a product
     * @param event click the delete product button
     * @throws IOException
     */
    @FXML private void deleteProductClick(ActionEvent event) throws IOException{

        //get the data for the selected product
        Product selectedProduct = null;
        selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        //make sure a product was selected
        if(selectedProduct == null){
            errorLabel.setText("Please select a product to delete");
        }

        else {
            if(selectedProduct.getAllAssociatedParts().size()>0){
                errorLabel.setText("Please remove all associated parts before deleting");
            }

            else {
                //confirmation of delete pop up
                confirmation.setHeaderText("Are you sure you would like to delete this product?");
                confirmation.setContentText(selectedProduct.getName());
                Optional<ButtonType> option = confirmation.showAndWait();
                //make sure a button is pressed
                if (option.get() == null) {
                    errorLabel.setText("Please select a button to proceed");
                }
                //if ok is pressed attempt to delete and report success/failure
                else if (option.get() == ButtonType.OK) {
                    boolean deleted = Inventory.deleteProduct(selectedProduct);
                    if (deleted) {
                        errorLabel.setText("Product deleted");
                    } else {
                        errorLabel.setText("Product deletion failed, product not found");
                    }
                }
                //if cancel is pressed let user know deletion was canceled
                else {
                    errorLabel.setText("Product deletion cancelled");
                }
            }
        }
    }

    /**
     *
     * @param event Text entered into part search field
     * @throws IOException
     */
    @FXML private void filterPartText(ActionEvent event) throws IOException{
        filterPart();
    }

    /**
     * Filters the part table based on input
     */
    private void filterPart(){
        partSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            //try catch to see if input is an ID(integer) or name search
            //try will execute if it is an integer
            try{
               int id = Integer.parseInt(newValue);
               Part filteredPart = Inventory.lookUpPart(id);
               ObservableList<Part> idFilteredList = FXCollections.observableArrayList();
               idFilteredList.add(filteredPart);
               partsTableView.setItems(idFilteredList);
            }
            //catch will execute if it isn't
            catch(NumberFormatException e){
               SortedList<Part> sortedParts = new SortedList<>(Inventory.lookUpPart(newValue));
               sortedParts.comparatorProperty().bind(partsTableView.comparatorProperty());
               partsTableView.setItems(sortedParts);
            }

        });
    }

    /**
     *
     * @param event text entered into product search field
     * @throws IOException
     */
    @FXML private void filterProductText(ActionEvent event) throws IOException{
        filterProduct();
    }
    /**Method that filters products based on text input*/
    private void filterProduct(){
        productSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            //if the text box input is integers look up by ID, if not look up by name
            try{
                int id = Integer.parseInt(newValue);
                Product filteredProduct = Inventory.lookUpProduct(id);
                ObservableList<Product> idFilteredList = FXCollections.observableArrayList();
                idFilteredList.add(filteredProduct);
                productTableView.setItems(idFilteredList);
            }
            catch(NumberFormatException e){
                SortedList<Product> sortedProducts = new SortedList<>(Inventory.lookUpProduct(newValue));
                sortedProducts.comparatorProperty().bind(productTableView.comparatorProperty());
                productTableView.setItems(sortedProducts);
            }

        });
    }


    /**
     *Pops up confirmation boxes and exits application
     * @param event exit button clicked
     * @throws IOException
     */
    @FXML private void exitClick(ActionEvent event) throws IOException{
        //confirmation of exit pop up
        confirmation.setHeaderText("Are you sure you would like to Exit?");
        confirmation.setContentText("");
        Optional<ButtonType> option = confirmation.showAndWait();
        //make sure a button is pressed
        if (option.get() == null) {
            errorLabel.setText("Please select a button to proceed");
        }
        //if ok is pressed close the application
        else if (option.get() == ButtonType.OK) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
        //if cancel is pressed let user know exit was canceled
        else {
            errorLabel.setText("Exit cancelled");
        }
    }
    //variables used to generate part and product ID
    public static int partIDCounter = 1;
    public static int productIDCounter = 1;


    /**
     *Initialize the tables and filters on the main screen
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //load data into the parts table if there is any
        if(Inventory.getAllParts().size()>0) {
            partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partCostColumn.setCellValueFactory(new PropertyValueFactory<>( "price"));
            partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

            partsTableView.setItems(Inventory.getAllParts());
        }

        //load data into the products table if there is any
        if(Inventory.getAllProducts().size()>0){
            productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

            productTableView.setItems(Inventory.getAllProducts());
        }

        filterPart();
        filterProduct();

    }
}