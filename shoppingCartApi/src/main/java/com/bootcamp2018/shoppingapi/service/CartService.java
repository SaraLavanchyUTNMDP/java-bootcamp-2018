package com.bootcamp2018.shoppingapi.service;


import com.bootcamp2018.shoppingapi.model.*;
import com.bootcamp2018.shoppingapi.repository.*;
import com.bootcampglobant.userregister.controller.UserService;
import com.bootcampglobant.userregister.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Lavanchy
 */

@Service
public class CartService {
	@Getter
	@Setter
	private ShoppingCart shoppingCart;
    @Autowired
    private IShoppingRepository shoppingRepository;
	@Autowired
	private  IItemLineRepository itemLineRepository;
	@Autowired
	private IOrder orderRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ItemService itemService;


	public CartService(){}


	public void	initializingCart(User user)  {
		userService = new UserService();
		if(!Objects.isNull(userService.findAnUser(user.getUsername()))){
			shoppingCart.setUser(user);
		}
		shoppingRepository.save(shoppingCart);
	}

	/** to add a new itemLine in the cart, if the item don´t exists, it will throw an exception
	 *
	 * @param description
	 * @param quantity
	 */
    public void addItemLine(String description, int quantity) throws Exception {
		Item anItem = itemService.getItem(description);
		if(Objects.nonNull(anItem)) {
			ItemLines itemLine = new ItemLines(anItem, quantity, shoppingCart);
			itemLineRepository.save(itemLine);
			shoppingRepository.savePrice(shoppingCart.getTotalPrice(), shoppingCart.getId());
		}else{
			throw new Exception("the item is not available");
		}
    }

	/** to find the last cart of the user by its username
	 *
	 * @param username the username of the User
	 * @return the last shopping cart if exists in the username
	 */
	public ShoppingCart getLastCart(String username){
    	return shoppingRepository.findAllByUserId(userService.findAnUser(username).getId()).get(0);
	}

    /** this method return all the cart's lines of item
     *
     * @return List of ItemLines, the items
     */


    /** this method returns an specific item that exist in the cart
     *
     * @param description the item name
     * @return ItemLines the line of the item
     */
    public ItemLines findLineByDescription(User user, String description){
    	ShoppingCart cart = this.getCart(user);
    	ItemLines myLine = null;
    	List<ItemLines> lines = itemLineRepository.findByMyItem(itemService.getItem(description));
		for(ItemLines line : lines){
			if(line.getShoppingCart().getId() == cart.getId()){
				myLine = line;
			}
		}
    	return myLine;
    }

    /** this method remove an item of the cart
     *
     * @param description the item name
     */
    public void deleteItemByDescription(User user, String description)  throws Exception{
    	Item item = itemService.getItem(description);
    	try {
    		if(Objects.nonNull(item)) {
				itemLineRepository.deleteByMyItemAndShoppingCart(item, this.getCart(user));
			}else{
    			throw new Exception("the description of the item is incorrect");
			}
		}catch (Exception e){
    		e.getMessage();
		}
    }

    /** this method change the quantity of  a specific item in the cart
     *
     * @param description the item name
     * @param quantity the new quantity
     */
    public void updateItemLine(User user,  String description, int quantity) throws Exception {
		Item theItem = itemService.getItem(description);
        if(Objects.nonNull(theItem)) {
        	try {
        		ShoppingCart cart = this.getCart(user);
        		double newPrice = quantity*(this.findLineByDescription(user, description).getMyItem().getPrice());
				itemLineRepository.updateByItem(theItem.getId(), quantity, cart.getId(), newPrice);
				shoppingRepository.savePrice(this.getTotalPrice(cart), cart.getId());
			}catch (Exception e){
        		e.getMessage();
			}
		}else{
        	throw new Exception("the description of the item is incorrect");
		}
    }

    /** this method returns all the lines of the cart with the total price and clear the cart.
     *
     * @return ShoppingRepository the item's lines and the total price
     */
    public ShoppingCart buyCart(User user){
		ShoppingCart mycart = this.getCart(user);
    	Order order = new Order(mycart, Calendar.getInstance().getTime(), this.getTotalPrice(mycart));
    	orderRepository.save(order);
    	return mycart;
    }

    /** this method return the price of an specific itemLine
	 *
	 * @param description the item name
	 * @return double the price
	 */
	public double getLinePrice(User user, String description){
		return this.findLineByDescription(user, description).getLinePrice();
	}

    /** this method returns the total price of the cart
     *
     * @return double the total price
     */
    public  double getTotalPrice(ShoppingCart cart){
    	double price = 0;
    	List<ItemLines> myLines = this.findallLines(cart);
    	for (ItemLines line : myLines){
    		price += line.getLinePrice();
		}
		return price;
    }

	public Order getCartOrder(long id) {
    	ShoppingCart cart = shoppingRepository.findById(id);
    	return orderRepository.findByShoppingCart(cart);
	}

	public void setShoppingCart(User user, ShoppingCart cart){
    	this.shoppingCart = cart;
    	if(Objects.isNull(this.shoppingRepository.findById(cart.getId()))) {
			if (Objects.isNull(this.getCart(user)))
				this.shoppingRepository.save(cart);
			else
				this.shoppingCart = this.getCart(user);
		}
	}

	public ShoppingCart getCart(User user) {
    	List<ShoppingCart> carts = shoppingRepository.findAllByUserId(user.getId());
    	ShoppingCart myCart = null;
    	for(int i=0; i<carts.size(); i++){
    		Order order = orderRepository.findByShoppingCart(carts.get(i));
    		if(Objects.isNull(order)){
    			myCart = carts.get(i);
			}
		}
		return  myCart;
	}

	public List<ItemLines> findallLines(ShoppingCart cart) {
    	return itemLineRepository.findByShoppingCart(cart);
	}
}