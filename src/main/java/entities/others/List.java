package entities.others;

import enums.ListTypes;

public class List {
    private long id;
    private ListTypes listType;

    // Constructor
    public List(long id, ListTypes listType) {
        this.id = id;
        this.listType = listType;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ListTypes getListType() {
        return listType;
    }

    public void setListType(ListTypes listType) {
        this.listType = listType;
    }

    @Override
    public String toString() {
        return "List{" +
                "id=" + id +
                ", listType=" + listType +
                '}';
    }
}
