package com.bootcamp2018.shoppingapi;

import com.bootcamp2018.shoppingapi.model.Item;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ItemTest {
    private Item anItem;
    private Item otherItem;

    @Before
    public void initTheTest(){
        anItem = new Item("VanGoh's ear", 5000);
        otherItem = new Item("Batman's cup", 30);
    }

    @Test
    public void createOneItem(){
        Assert.assertTrue(anItem != null);
        Assert.assertEquals(anItem.getDescription(),"VanGoh's ear");
        Assert.assertEquals(anItem.getPrice(), 5000, 0);
    }

    @Test
    public void creatingItems(){
        Assert.assertTrue(anItem != null);
        Assert.assertEquals(anItem.getDescription(),"VanGoh's ear");
        Assert.assertEquals(anItem.getPrice(),5000.0,0);
        Assert.assertTrue(otherItem != null);
        Assert.assertEquals(otherItem.getDescription(),"Batman's cup");
        Assert.assertEquals(otherItem.getPrice(),30.0, 0);
    }

    @Test
    public void changePrice(){
        anItem.changePrice(40);
        Assert.assertEquals(anItem.getPrice(),40.0,0);
    }

}
