package Model;
/**
 *
 * @author Andrew Stowe
 */
public class OutsourcedPart extends Part {

    private String companyName;

    //outsourced part constructor
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @return name of company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
