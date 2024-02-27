package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

/**
 *
 * @author Andrew Stowe
 */

public class Inventory {

    //where data will be stored for viewing
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**@param newPart add part to inventory */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }


    /**@param newProduct add product to inventory */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**@param partId looking up part with this ID
     *@return the part with the given id or null if not found*/
    public static Part lookUpPart(int partId){
       //loop through parts till part with given id is found
        for(Part p : allParts){
            if (p.getId() == partId){
                return p;
            }
        }

        //if the part isn't found return null
        return null;
    }

    /**@param productID method for looking up product with ID
     *@return the product with the given id or null if not found*/
    public static Product lookUpProduct(int productID){
        //loop through the list of products until the given product id is found
        for(Product p : allProducts){
            if(p.getId() == productID){
                return p;
            }
        }

        //if the product isn't found return null
        return null;
    }

    /**@param partName a name to search for in the parts list
     * @return the parts which include the searched name*/
    public static ObservableList<Part> lookUpPart (String partName){
        //wrap the all parts list into a filtered list
        FilteredList<Part> filteredParts = new FilteredList<>(allParts, p-> true);

        //filter the new filtered list based on input
        filteredParts.setPredicate(part-> {
            //if the filter is blank or null don't filter
            if(partName == null || partName.isEmpty()){
                return true;
            }
            //compare part names to filter text all in lower case
            else if(part.getName().toLowerCase().contains(partName.toLowerCase())){
                return true;
            }
            else {
                return false;
            }

        });

        return filteredParts;
    }

    /**@param productName a name to search for in the products list
     * @return the products that include the searched product name
     */
    public static ObservableList<Product> lookUpProduct (String productName){
        //wrap the all products list into a filtered list
        FilteredList<Product> filteredProducts = new FilteredList<>(allProducts, p-> true);

        //filter the new filtered list based on input
        filteredProducts.setPredicate(product-> {
            //if the filter is blank or null don't filter
            if(productName == null || productName.isEmpty()){
                return true;
            }
            //compare part names to filter text all in lower case
            else if(product.getName().toLowerCase().contains(productName.toLowerCase())){
                return true;
            }
            else {
                return false;
            }

        });

        return filteredProducts;

    }

    /**@param index the index of the part to update
     * @param selectedPart the updated part which will replace to old part
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     *
     * @param index the index of the product to update
     * @param selectedProduct the updated product which will replace the old product
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**
     *
     * @param selectedPart the part which we will attempt to delete
     * @return true if part is found and deleted, false if not
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     *
     * @param selectedProduct the product which we will attempt to delete
     * @return true if the product is found and deleted, false if not
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }


    /**
     *
     * @return list of all parts in inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return list of all products in inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
