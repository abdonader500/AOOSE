package aoose_main.entities.others;

import aoose_main.enums.ListTypes;
import aoose_main.entities.actors.Cashier;
import aoose_main.enums.Shifts;

public class List<I> extends Cashier {
    private long ID;
    private ListTypes listType;

    // Constructor
    public List(int id, String fullName, String email, String password, long phoneNumber, long pcNumber,
                Shifts shiftSchedule, int salary, long listID, ListTypes listType) {
        super(id, fullName, email, password, phoneNumber, pcNumber, shiftSchedule, salary); // Call Cashier constructor
        this.ID = listID;
        this.listType = listType;
    }


    // setters and getters
    public void setID(long ID) {
        this.ID = ID;
    }

    public void setListType(ListTypes listType) {
        this.listType = listType;
    }

    public long getID() {
        return ID;
    }

    public ListTypes getListType() {
        return listType;
    }
}
