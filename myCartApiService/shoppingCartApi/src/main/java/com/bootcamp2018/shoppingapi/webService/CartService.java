package com.bootcamp2018.shoppingapi.webService;


import com.bootcamp2018.shoppingapi.model.Item;
import com.bootcamp2018.shoppingapi.model.ItemLines;
import com.bootcamp2018.shoppingapi.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;

/**
 *
 * @author Lavanchy
 */

@RestController
@RequestMapping(value = "/Cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartService {


    private ShoppingCart myCart = new ShoppingCart();

    /** this method add a new Item in the cart
     *
     * @param description the name of the Item
     * @param price the price of the Item
     */
    @RequestMapping("/item/add")
    public void add(@RequestParam("description") String description, @RequestParam("price") Double price){
        Item anItem= new Item(description, price);
        myCart.addItem(anItem);
    }

    /** this method add a new item in the cart with an specific quantity
     *
     * @param description the name of the item
     * @param price the price of the item
     * @param quantity the quantity of items.
     */
    @RequestMapping("/item/add/quantity")
    public void add(@RequestParam("description") String description, @RequestParam("price") Double price,
                    @RequestParam("quantity") int quantity){
        Item anItem= new Item(description, price);
        myCart.addItem(anItem, quantity);
    }

    /** this method return all the cart's lines of item
     *
     * @return <List<ItemLines>> the items
     */
    @RequestMapping("/item/all")
    public List<ItemLines> findall(){
        return myCart.getLines();
    }

    /** this method returns an specific item that exist in the cart
     *
     * @param description the item name
     * @return <ItemLines> the line of the item
     */
    @RequestMapping(value = "/item")
    public ItemLines findOne( @RequestParam("description") String description){
       return myCart.getLineByName(description);
    }

    /** this method remove an item of the cart
     *
     * @param description the item name
     */
    @RequestMapping(value = "/item/delete")
    public void deleteItem(@RequestParam("description") String description){
        myCart.deleteItem(description);
    }

    /** this method change the quantity of  a specific item in the cart
     *
     * @param description the item name
     * @param quantity the new quantity
     */
    @RequestMapping(value = "/item/update/quantity")
    public void updateItem(@RequestParam("description") String description, @RequestParam("quantity") int quantity){
        myCart.changeItemQuantity(description,quantity);
    }

    /** this method returns all the lines of the cart with the total price and clear the cart.
     *
     * @return <ShoppingCart> the item's lines and the total price
     */
    @RequestMapping(value = "/item/buy")
    public ShoppingCart buyCart(){
        return myCart.buyItems();
    }

    /** this method return the price of an specific itemLine
     *
     * @param description the item name
     * @return <double> the price
     */
    @RequestMapping(value ="/price/line")
    public double getLinePrice(@RequestParam("description") String description){
        return myCart.getLineByName(description).getLinePrice();
    }

    /** this method returns the total price of the cart
     *
     * @return <double> the total price
     */
    @RequestMapping(value = "/price/all")
    public  double getTotalPrice(){
        return myCart.getTotalPrice();
    }




}