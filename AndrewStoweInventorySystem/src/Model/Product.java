package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * * @author Andrew Stowe
 */

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**@return product ID*/
    public int getId() {
        return id;
    }

    /**@param id the id to set*/
    public void setId(int id) {
        this.id = id;
    }

    /**@return product name*/
    public String getName() {
        return name;
    }

    /**@param name product name to set*/
    public void setName(String name) {
        this.name = name;
    }

    /**@return product price*/
    public double getPrice() {
        return price;
    }

    /**@param price product price to set*/
    public void setPrice(double price) {
        this.price = price;
    }

    /**@return product stock*/
    public int getStock() {
        return stock;
    }

    /**@param stock product stock to set*/
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**@return product minimum*/
    public int getMin() {
        return min;
    }

    /**@param min set product minimum*/
    public void setMin(int min) {
        this.min = min;
    }

    /**@return product maximum*/
    public int getMax() {
        return max;
    }

    /**@param max set product maximum*/
    public void setMax(int max) {
        this.max = max;
    }

    /**Add a part to a product
     * @param part part to add */
    public void addAssociatedPart (Part part){
        this.associatedParts.add(part);
    }

    /**
     * try to remove a part from a product
     * @param selectedAssociatedPart the part to remove
     * @return true if successful */
    public boolean deleteAssociatedPart (Part selectedAssociatedPart){
         return this.associatedParts.remove(selectedAssociatedPart);
    }


    /** @return a list of parts associated with product */
    public ObservableList<Part> getAllAssociatedParts (){
        return associatedParts;
    }
}
