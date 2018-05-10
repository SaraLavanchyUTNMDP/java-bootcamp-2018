package com.bootcamp2018.shoppingapi.model;

import java.util.Objects;

public class ItemLines {
    private Item myItem;
    private int Quantity;

    public ItemLines(){}

    public ItemLines(Item item) {
        this.myItem = item;
        this.Quantity = 1;
    }

    public ItemLines(Item item, int quantities){
        this.myItem = item;
        this.Quantity = quantities;
    }

    /** Compare if the Item of this line is the same of @param o Item
     *
     * @param o the item that we want to compare. It has to be an instance of Item.
     * @return true if @param o is the item that the line contains.
     */
    @Override
    public boolean equals(Object o) {
        boolean areEquals = false;
        if(o != null && o instanceof Item){
            if((o.equals(this.myItem))){
                areEquals =true;
            }
        }
        return areEquals;
    }

    @Override
    public int hashCode() {

        return Objects.hash(myItem, Quantity);
    }

    public Item getItem() {
        return this.myItem;
    }

    public int getQuantity() {
        return this.Quantity;
    }

    public void setQuantity(int newQuantity) {
        this.Quantity = newQuantity;
    }

    public double getLinePrice() {
        return this.Quantity*this.getItem().getPrice();
    }
}
