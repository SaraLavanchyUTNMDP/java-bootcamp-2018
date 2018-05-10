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

    @RequestMapping("/item/add")
    public void add(@RequestParam("description") String description, @RequestParam("price") Double price){
        Item anItem= new Item(description, price);
        myCart.addItem(anItem);
    }

    @RequestMapping("/item/add/quantity")
    public void add(@RequestParam("description") String description, @RequestParam("price") Double price,
                    @RequestParam("quantity") int quantity){
        Item anItem= new Item(description, price);
        myCart.addItem(anItem, quantity);
    }

    @RequestMapping("/item/all")
    public List<ItemLines> findall(){
        return myCart.getLines();
    }

    @RequestMapping(value = "/item")
    public ItemLines findOne( @RequestParam("description") String description){
       return myCart.getLineByName(description);
    }

    @RequestMapping(value = "/item/delete")
    public void deleteItem(@RequestParam("description") String description){
        myCart.deleteItem(description);
    }

    @RequestMapping(value = "/item/update/quantity")
    public void updateItem(@RequestParam("description") String description, @RequestParam("quantity") int quantity){
        myCart.changeItemQuantity(description,quantity);
    }

    @RequestMapping(value = "/item/buy")
    public ShoppingCart buyCart(){
        return myCart.buyItems();
    }

    @RequestMapping(value ="/price/line")
    public double getLinePrice(@RequestParam("description") String description){
        return myCart.getLineByName(description).getLinePrice();
    }

    @RequestMapping(value = "/price/all")
    public  double getTotalPrice(){
        return myCart.getTotalPrice();
    }




}