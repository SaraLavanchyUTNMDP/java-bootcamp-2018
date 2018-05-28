package com.bootcamp2018.shoppingapi.service;


import com.bootcamp2018.shoppingapi.model.*;
import com.bootcamp2018.shoppingapi.repository.*;
import com.bootcampglobant.userregister.controller.UserService;
import com.bootcampglobant.userregister.models.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Lavanchy
 */
@Data
@Service
public class CartService {

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


	public CartService(){
		shoppingCart = new ShoppingCart();
	}

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
			shoppingCart.addItem(anItem, quantity);
			shoppingRepository.savePrice(shoppingCart.getTotalPrice(), shoppingCart.getId());
		}else{
			throw new Exception("the item is not available");
		}
    }

    /** this method return all the cart's lines of item
     *
     * @return List of ItemLines, the items
     */

    public List<ItemLines> findallLines(){
    	return shoppingCart.getLines();
    }

    /** this method returns an specific item that exist in the cart
     *
     * @param description the item name
     * @return ItemLines the line of the item
     */
    public ItemLines findLineByDescription( String description){
       return shoppingCart.getLineByName(description);
    }

    /** this method remove an item of the cart
     *
     * @param description the item name
     */
    public void deleteItemByDescription(String description)  throws Exception{
    	Item item = itemService.getItem(description);
    	try {
    		if(Objects.nonNull(item)) {
				itemLineRepository.deleteByMyItem(item);
				shoppingCart.deleteItem(description);
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
    public void updateItemLine( String description, int quantity) throws Exception {
		Item theItem = itemService.getItem(description);
        if(Objects.nonNull(theItem)) {
        	try {
				itemLineRepository.updateByItem(theItem.getId(), quantity);
				shoppingCart.changeItemQuantity(description, quantity);
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
    public ShoppingCart buyCart(){
		ShoppingCart mycart = shoppingCart.buyItems();
    	Order order = new Order(mycart, Calendar.getInstance().getTime(), mycart.finalTotalPrice());
    	orderRepository.save(order);
    	return mycart;
    }

    /** this method return the price of an specific itemLine
     *
     * @param description the item name
     * @return double the price
     */
    public double getLinePrice(String description){
        return shoppingCart.getLineByName(description).getLinePrice();
    }

    /** this method returns the total price of the cart
     *
     * @return double the total price
     */
    public  double getTotalPrice(){
        return shoppingCart.getTotalPrice();
    }




}