package Model;
/**
 *
 * @author Andrew Stowe
 */
public class InHousePart extends Part{

    private int machineId;

    //In house part constructor
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @param machineId the machine id to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     *
     * @return machine id
     */
    public int getMachineId() {
        return machineId;
    }
}
