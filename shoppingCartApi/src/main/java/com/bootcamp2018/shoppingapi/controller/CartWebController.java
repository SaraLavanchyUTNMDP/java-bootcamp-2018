package com.bootcamp2018.shoppingapi.controller;

import com.bootcamp2018.shoppingapi.model.ShoppingCart;
import com.bootcamp2018.shoppingapi.service.CartService;
import com.bootcamp2018.shoppingapi.model.ItemLines;
import com.bootcampglobant.userregister.controller.UserService;
import com.bootcampglobant.userregister.models.User;
import com.bootcampglobant.userregister.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartWebController {

	CartService cartService= new CartService();
	UserService userService;

	/** initialice the shoppingCart, if the user exists, the cart will be created, else, it will throw an exception
	 *
	 * @param user String,the user Name
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void creatingMyCart(String userName){
		try {
			User user = userService.findAnUser(userName);
			this.cartService.initializingCart(user);
		}catch (Exception e){
			e.getMessage();
		}
	}

	/** to add a new itemLine with a specific quantity ot items
	 *
	 * @param description the name of the item
	 * @param quantity the quantity of the same item
	 */
	@RequestMapping(value = "/itemLine", method = RequestMethod.POST)
	public void add(@RequestParam("description") String description,
			@RequestParam("quantity") int quantity){
		try {
			this.cartService.addItemLine(description, quantity);
		}catch (Exception e){
			e.getMessage();
		}
	}

	/** to find all the itemLines of the cart
	 *
	 * @return a List of ItemLines with all the itemLines of the cart
	 */
	@RequestMapping(value = "/itemLines", method = RequestMethod.GET)
	public List<ItemLines> findAll(){
		return this.cartService.findallLines();
	}

	/** calls the cart controller to find a specific item
	 *
	 * @param description the name of the item
	 * @return the ItemLines of the item
	 */
	@RequestMapping(value = "/itemLine", method = RequestMethod.GET)
	public ItemLines findOne( @PathVariable("description") String description) {
		return this.cartService.findLineByDescription(description);
	}


	/** to find a specific item an delete its line. throws an exception if the description dont match any item in the repository
	 *
	 * @param description the name of the item
	 */
	@RequestMapping(value = "/itemLine", method = RequestMethod.DELETE)
	public void deleteItem(@RequestParam("description") String description){
		try {
			this.cartService.deleteItemByDescription(description);
		}catch (Exception e){
			e.getMessage();
		}
	}

	/** to change the quantity of an item in an itemLine
	 *
	 * @param description the name of the item
	 * @param quantity the new Quantity
	 */
	@RequestMapping(value = "/itemLine", method = RequestMethod.PATCH)
	public void updateItem(@RequestParam("description") String description, @RequestParam("quantity") int quantity) {
		try {
			this.cartService.updateItemLine(description, quantity);
		}catch (Exception e){
			e.getMessage();
		}
	}

	/** to make the order and clear the shopping cart after return it
	 *
	 * @return the shopping cart
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ShoppingCart buyCart() {
		return this.cartService.buyCart();
	}

	/** to obtain the price of a specific line of items
	 *
	 * @param description the name of the item
	 * @return double line price
	 */
	@RequestMapping(value ="/ItemLine/price", method = RequestMethod.GET)
	public double getLinePrice(@PathVariable("description") String description){
		return this.cartService.getLinePrice(description);
	}

	/** to obtain the total price of the cart
	 *
	 * @return a double total price
	 */
	@RequestMapping(value = "/price", method = RequestMethod.GET)
	public  double getTotalPrice(){
		return this.cartService.getTotalPrice();
	}

}