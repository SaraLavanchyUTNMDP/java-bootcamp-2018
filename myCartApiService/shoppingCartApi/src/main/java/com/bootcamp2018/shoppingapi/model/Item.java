package com.bootcamp2018.shoppingapi.model;

import java.util.Objects;

public class Item {
    private String description;
    private Double price;

    
    public Item(String item, double thePrice) {
        this.description = item;
        this.price = thePrice;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getPrice() {
        return this.price;
    }

    public void changePrice(double newPrice) {
        this.price = newPrice;
    }

    /** Compare two Items and return true if the items have the same description
     *
      * @param o the item to compare
     * @return true if the items have the same description and false if not.
     */
    @Override
    public boolean equals(Object o) {
        boolean areEquals = false;
        if(!Objects.isNull(o) && o instanceof Item){
            if((((Item) o).description.equals(this.description))){
                areEquals =true;
            }
        }
        return areEquals;
    }

    @Override
    public int hashCode() {

        return Objects.hash(description, price);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
