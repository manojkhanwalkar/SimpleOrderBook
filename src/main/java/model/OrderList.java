package model;

import java.util.Date;
import java.util.List;

public class OrderList {

    String listId;
    String parentListId;
    List<ConstituentList> constituents;
    List<String> orderIds;
    String name ;
    String client ;
    Date date ;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getParentListId() {
        return parentListId;
    }

    public void setParentListId(String parentListId) {
        this.parentListId = parentListId;
    }

    public List<ConstituentList> getConstituents() {
        return constituents;
    }

    public void setConstituents(List<ConstituentList> constituents) {
        this.constituents = constituents;
    }

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
