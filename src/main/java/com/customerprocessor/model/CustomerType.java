package com.customerprocessor.model;

public enum CustomerType {
    VALID("valid_customers"),
    INVALID("invalid_customers");
    private final String table;

    CustomerType(String table) {
        this.table = table;
    }

    public boolean isEqual(CustomerType customerType){
        return this.getTable().equals(customerType.getTable());
    }

    public String getTable() {
        return table;
    }
}
