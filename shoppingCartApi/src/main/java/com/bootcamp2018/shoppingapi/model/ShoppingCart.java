package com.bootcamp2018.shoppingapi.model;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;


public class ShoppingCart {

    List<ItemLines> myItemLines;
    double totalPrice;

    public ShoppingCart() {
        this.myItemLines = new ArrayList<ItemLines>();
    }

    /**
     * this method verify if the card has an item or not
     *
     * @return true if there is no lines in the cart,
     * and false if the cart has one or more lines
     */
    public boolean isEmpty() {
        return myItemLines.isEmpty();
    }

    /** this method add an item in the cart
     *
      * @param anItem, the item that we want to add in the cart
     */
    public void addItem(@WebParam Item anItem) {
        boolean existTheItem = false;
        for ( ItemLines line : myItemLines){
            if(line.getItem().equals(anItem)){
                line.setQuantity(line.getQuantity()+1);
                existTheItem = true;
            }
        }
        if(existTheItem == false){
            ItemLines myLine = new ItemLines(anItem);
            this.myItemLines.add(myLine);
        }

    }

    /**this method add more of  one item of the same type in the cart
     *
     * @param anItem the item that we want to add.
     * @param cant the Quantity of items of the same type that we want to add
     */
    public void addItem(Item anItem, int cant) {
        boolean existTheItem = false;
        for ( ItemLines line : myItemLines){
            if(line.getItem().equals(anItem)){
                line.setQuantity(line.getQuantity()+cant);
                existTheItem = true;
            }
        }
        if(existTheItem == false){
            ItemLines myLine = new ItemLines(anItem, cant);
            this.myItemLines.add(myLine);
        }
    }

    /** to know if the cart contains an item
     *
     * @param anItem: the item that we want to known if exists in the cart.
     * @return true if the item exists and false if doesnt exists
     */
    public boolean contains(Item anItem) {
        boolean contains = false;
        for(ItemLines line : this.myItemLines){
            if(line.getItem().equals(anItem)){
                contains=true;
            }
        }
        return contains;
    }

    /** this method returns all the lines
     *
     * @return all the lines
     */
    public List<ItemLines> getLines() {
        return this.myItemLines;
    }

    /** this method returns the line of an specific Item.
     *
     * @param description: the Item Line that we want to search
     * @return the Item Line
     */
    public ItemLines getLineByName(String description) {
        ItemLines theLine = null;
        for(ItemLines line : this.myItemLines){
            if(line.getItem().getDescription().equals(description)){
                theLine = line;
            }
        }
        return theLine;
    }

    /** this Method returns the Quantity of Items in the cart
     *
     * @return int quantity of items
     */
    public int getItemsQuantity() {
        return this.myItemLines.size();
    }

    public void changeItemQuantity(String description, int i) {
        for (ItemLines line : myItemLines){
            if(line.getItem().getDescription().equals(description)){
                line.setQuantity(i);
            }
        }
    }

    /** this method remove the line of an item of the cart
     *
     * @param description the item that we want to remove
     */
    public void deleteItem(String description) {
        for (int i=0; i<this.myItemLines.size();i++){
            if(this.myItemLines.get(i).getItem().getDescription().equals(description)){
                myItemLines.remove(i);
            }
        }
    }


    /**
     *
     * @return the list of the Item
     */
    public ShoppingCart buyItems() {
        ShoppingCart cart = (ShoppingCart) this.clone();
        this.totalPrice = 0;
        this.myItemLines = new ArrayList<>();
        return cart;
    }

    public double getTotalPrice() {
        for (ItemLines line : myItemLines){
            this.totalPrice+=line.getLinePrice();
        }
        return this.totalPrice;
    }

    @Override
    protected Object clone() {
        ShoppingCart theCloneCart = new ShoppingCart();
        try{
            theCloneCart.setMyItemLines(this.getLines());
            theCloneCart.setTotalPrice(this.finalTotalPrice());

        }catch(Exception e){}
        return theCloneCart;
    }


    public void setMyItemLines(List<ItemLines> myItemLines) {
        this.myItemLines = myItemLines;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double finalTotalPrice(){
        return this.totalPrice;
    }

}
