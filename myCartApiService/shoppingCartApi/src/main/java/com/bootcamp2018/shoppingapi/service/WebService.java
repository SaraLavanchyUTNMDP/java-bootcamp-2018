package com.bootcamp2018.shoppingapi.service;

import com.bootcamp2018.shoppingapi.controller.CartController;
import com.bootcamp2018.shoppingapi.model.ItemLines;
import com.bootcamp2018.shoppingapi.repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(value = "/Cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebService {

	@Autowired
	CartController cartController;

	/** calls the cart controller and add a new item in the cart
	 *
	 * @param description the item name
	 * @param price the unity price of the item
	 */
	@RequestMapping("/item/add")
	public void add(@RequestParam("description") String description, @RequestParam("price") Double price) {
		this.cartController.add(description,price);
	}

	/** calls the cart controller and add a new itemLine with a specific quantity ot items
	 *
	 * @param description the name of the item
	 * @param price the unity price of the item
	 * @param quantity the quantity of the same item
	 */
	@RequestMapping("/item/add/quantity")
	public void add(@RequestParam("description") String description, @RequestParam("price") Double price,
			@RequestParam("quantity") int quantity){
		this.cartController.add(description,price,quantity);
	}

	/** calls the cart controller to find all the itemLines of the cart
	 *
	 * @return a List of ItemLines with all the itemLines of the cart
	 */
	@RequestMapping("/item/all")
	public List<ItemLines> findAll(){
		return this.cartController.findall();
	}

	/** calls the cart controller to find a specific item
	 *
	 * @param description the name of the item
	 * @return the ItemLines of the item
	 */
	@RequestMapping(value = "/item")
	public ItemLines findOne( @RequestParam("description") String description) {
		return this.cartController.findOne(description);
	}

	/** calls the cart controller to find a specific item an delete its line
	 *
	 * @param description the name of the item
	 */
	@RequestMapping(value = "/item/delete")
	public void deleteItem(@RequestParam("description") String description){
		this.cartController.deleteItem(description);
	}

	/** calls the cart controller to change the quantity of an item
	 *
	 * @param description the name of the item
	 * @param quantity the new Quantity
	 */
	@RequestMapping(value = "/item/update/quantity")
	public void updateItem(@RequestParam("description") String description, @RequestParam("quantity") int quantity) {
		this.cartController.updateItem(description, quantity);
	}

	/** calls the cart controller and clear the shopping cart after return it
	 *
 	 * @return the shopping cart
	 */
	@RequestMapping(value = "/item/buy")
	public ShoppingRepository buyCart() {
		return this.cartController.buyCart();
	}

	/** calls the cart controller to obtain the price of a specific line of items
	 *
	 * @param description the name of the item
	 * @return double line price
	 */
	@RequestMapping(value ="/price/line")
	public double getLinePrice(@RequestParam("description") String description){
		return this.cartController.getLinePrice(description);
	}

	/** calls the cart controller to obtain the total price of the cart
	 *
	 * @return a double total price
	 */
	@RequestMapping(value = "/price/all")
	public  double getTotalPrice(){
		return this.cartController.getTotalPrice();
	}
}