package com.bootcamp2018.shoppingapi;

import com.bootcamp2018.shoppingapi.model.Item;
import com.bootcamp2018.shoppingapi.model.ShoppingCart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShoppingCartTest {

    private ShoppingCart myCart;
    Item anItem;
    Item otherItem;

    @Before
    public void creatingMyClasses(){
        myCart = new ShoppingCart();
        anItem = new Item("VanGoh's ear", 5000);
        otherItem = new Item("Batman's cup", 30);
    }

    @Test
    public void firstTime(){
        Assert.assertFalse(myCart == null);
        Assert.assertTrue(myCart.isEmpty());
    }

    @Test
    public void addItem(){
        myCart.addItem(anItem);
        Assert.assertTrue(myCart.contains(anItem));
        Assert.assertEquals(myCart.getLines().size(),1);
        Assert.assertEquals(myCart.getLineByName(anItem.getDescription()).getQuantity(),1);
    }

    @Test
    public void addMultipleItems(){
        myCart.addItem(anItem);
        myCart.addItem(otherItem);
        Assert.assertTrue(myCart.contains(anItem));
        Assert.assertTrue(myCart.contains(otherItem));
        Assert.assertEquals(myCart.getLines().size(),2);
        Assert.assertEquals(myCart.getLineByName(anItem.getDescription()).getQuantity(),1);
        Assert.assertEquals(myCart.getLineByName(otherItem.getDescription()).getQuantity(),1);
        Assert.assertEquals(myCart.getItemsQuantity(),2);
    }

    @Test
    public void addTheSameItemAgain(){
        myCart.addItem(anItem);
        myCart.addItem(otherItem);
        myCart.addItem(anItem);
        Assert.assertEquals(myCart.getLines().size(),2);
        Assert.assertEquals(myCart.getLineByName(anItem.getDescription()).getQuantity(),2);
        Assert.assertEquals(myCart.getLineByName(otherItem.getDescription()).getQuantity(),1);
        Assert.assertEquals(myCart.getItemsQuantity(),2);
    }

    @Test
    public void changeQuantity(){
        myCart.addItem(anItem);
        myCart.changeItemQuantity(anItem.getDescription(),6);
        Assert.assertEquals(myCart.getLines().size(),1);
        Assert.assertEquals(myCart.getLineByName(anItem.getDescription()).getQuantity(),6);
        Assert.assertEquals(myCart.getItemsQuantity(),1);
    }

    @Test
    public void deleteAnItem(){
        myCart.addItem(anItem);
        myCart.deleteItem(anItem.getDescription());
        Assert.assertFalse(myCart.contains(anItem));
        Assert.assertEquals(myCart.getLines().size(),0);
    }


    @Test
    public void getTotalPrice(){
        myCart.addItem(anItem);
        myCart.addItem(otherItem);
        Assert.assertEquals(myCart.getTotalPrice(),5030, 0);
    }


    @Test
    public void getTotalPriceMoreOneItem(){
        myCart.addItem(anItem, 5);
        myCart.addItem(otherItem);
        Assert.assertEquals(myCart.getTotalPrice(),25030,0);
    }

    @Test
    public void buy(){
        myCart.buyItems();
        myCart.addItem(anItem);
        ShoppingCart buy = myCart.buyItems();
        Assert.assertEquals(5000, buy.getTotalPrice(),  0);
        Assert.assertFalse(buy.getLines().isEmpty());
        Assert.assertEquals(0, myCart.getLines().size());
        Assert.assertEquals(0, myCart.getItemsQuantity());
    }

}
