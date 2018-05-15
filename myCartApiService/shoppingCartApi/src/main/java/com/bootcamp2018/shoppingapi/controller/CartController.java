package com.bootcamp2018.shoppingapi.controller;


import com.bootcamp2018.shoppingapi.model.Item;
import com.bootcamp2018.shoppingapi.model.ItemLines;
import com.bootcamp2018.shoppingapi.repository.ShoppingRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 *
 * @author Lavanchy
 */
@Data
@RestController
public class CartController {

    @Autowired
    private ShoppingRepository myCart;

    /** this method add a new Item in the cart
     *
     * @param description the name of the Item
     * @param price the price of the Item
     */

    public void add(String description, Double price){
        Item anItem= new Item(description, price);
        myCart.addItem(anItem);
    }

    /** this method add a new item in the cart with an specific quantity
     *
     * @param description the name of the item
     * @param price the price of the item
     * @param quantity the quantity of items.
     */
    public void add(String description, Double price, int quantity){
        Item anItem= new Item(description, price);
        myCart.addItem(anItem, quantity);
    }

    /** this method return all the cart's lines of item
     *
     * @return List of ItemLines, the items
     */

    public List<ItemLines> findall(){
        return myCart.getLines();
    }

    /** this method returns an specific item that exist in the cart
     *
     * @param description the item name
     * @return ItemLines the line of the item
     */
    public ItemLines findOne( String description){
       return myCart.getLineByName(description);
    }

    /** this method remove an item of the cart
     *
     * @param description the item name
     */
    public void deleteItem(String description){
        myCart.deleteItem(description);
    }

    /** this method change the quantity of  a specific item in the cart
     *
     * @param description the item name
     * @param quantity the new quantity
     */
    public void updateItem( String description, int quantity){
        myCart.changeItemQuantity(description,quantity);
    }

    /** this method returns all the lines of the cart with the total price and clear the cart.
     *
     * @return ShoppingRepository the item's lines and the total price
     */
    public ShoppingRepository buyCart(){
        return myCart.buyItems();
    }

    /** this method return the price of an specific itemLine
     *
     * @param description the item name
     * @return double the price
     */
    public double getLinePrice(String description){
        return myCart.getLineByName(description).getLinePrice();
    }

    /** this method returns the total price of the cart
     *
     * @return double the total price
     */
    public  double getTotalPrice(){
        return myCart.getTotalPrice();
    }




}