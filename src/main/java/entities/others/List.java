package entities.others;

import entities.actors.Cashier;
import enums.ListTypes;
import enums.Shifts;

public class List extends Cashier {
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
