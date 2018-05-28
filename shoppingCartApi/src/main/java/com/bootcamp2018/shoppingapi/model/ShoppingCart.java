package com.bootcamp2018.shoppingapi.model;


import com.bootcampglobant.userregister.models.User;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jws.WebParam;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "carts")
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cart")
	private long id;

	@ManyToOne
	@JoinColumn(name="fk_iduser", referencedColumnName = "id_user", nullable = false)
	private User user;

	@OneToMany(mappedBy = "shoppingCart")
	private List<ItemLines> myItemLines;

    @Column(name = "date")
    private Date boughtDate;

	@Column(name = "totalprice")
    private double totalPrice;

	public ShoppingCart(){
		this.boughtDate = Calendar.getInstance().getTime();
		this.myItemLines = new ArrayList<>();
		this.totalPrice = 0;
	}

	public ShoppingCart(User theUser) {
    	this.user = theUser;
        this.boughtDate = Calendar.getInstance().getTime();
    	this.myItemLines = new ArrayList<>();
    	this.totalPrice = 0;
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


    /**this method add more of  one item of the same type in the cart
     *
     * @param anItem the item that we want to add.
     * @param cant the Quantity of items of the same type that we want to add
     */
    public void addItem(Item anItem, int cant) {
        boolean existTheItem = false;
        for ( ItemLines line : myItemLines){
            if(line.getMyItem().equals(anItem)){
                line.setQuantity(line.getQuantity()+cant);
                existTheItem = true;
                this.totalPrice = this.getTotalPrice();
            }
        }
        if(existTheItem == false){
            ItemLines myLine = new ItemLines(anItem, cant, this);
            this.myItemLines.add(myLine);
            this.totalPrice = myLine.getLinePrice();
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
            if(line.getMyItem().equals(anItem)){
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
            if(line.getMyItem().getDescription().equals(description)){
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
            if(line.getMyItem().getDescription().equals(description)){
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
            if(this.myItemLines.get(i).getMyItem().getDescription().equals(description)){
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

	/** calculate the total price of the cart
	 *
	 * @return a double total price
	 */
	public double getTotalPrice() {
        for (ItemLines line : myItemLines){
            this.totalPrice+=line.getLinePrice();
        }
        return this.totalPrice;
    }

	/** create another instance of shopping cart that is equals to this one.
	 *
	 * @return a ShoppingCart
	 */
	@Override
    protected Object clone() {
		ShoppingCart theCloneCart = new ShoppingCart(this.user);
        try{
        	theCloneCart.setBoughtDate(this.getBoughtDate());
            theCloneCart.setMyItemLines(this.getLines());
            theCloneCart.setTotalPrice(this.finalTotalPrice());

        }catch(Exception e){}
        return theCloneCart;
    }

	/** returns the total price of the cart
	 *
	 * @return double finalPrice
	 */
	public double finalTotalPrice(){
        return this.totalPrice;
    }

}
