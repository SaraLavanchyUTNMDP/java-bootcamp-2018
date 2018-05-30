package com.bootcamp2018.shoppingapi.controller;

import com.bootcamp2018.shoppingapi.Request.ItemLineRequest;
import com.bootcamp2018.shoppingapi.model.ShoppingCart;
import com.bootcamp2018.shoppingapi.service.CartService;
import com.bootcamp2018.shoppingapi.model.ItemLines;
import com.bootcampglobant.userregister.controller.UserService;
import com.bootcampglobant.userregister.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartWebController {

	@Autowired
	private CartService cartService;
	private UserService userService;
	private HttpSession session;


	/** to initialice the shoppingCart. if the user exists, the cart will be created, else, it will throw an exception
	 *
	 * @param userName the name of the user
	 * @return a ResponseEntity<ShoppingCart>
	 */
	@RequestMapping(value = "/user/{username}", method = RequestMethod.POST)
	public ResponseEntity<ShoppingCart> creatingMyCart(@PathVariable("username") String userName){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "CartWebController");
		try {
			User user = userService.findAnUser(userName);
			this.cartService.initializingCart(user);
		}catch (Exception e){
			e.getMessage();
		}
		return ResponseEntity.accepted().headers(headers).body(cartService.getShoppingCart());
	}

	/** to add a new itemLine with a specific quantity ot items
	 */
	@RequestMapping(value = "/itemLine", method = RequestMethod.POST)
	public ResponseEntity add(HttpServletRequest request, @RequestBody ItemLineRequest itemLineRequest) throws Exception {
		session = request.getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "CartWebController");
		try {
			if(Objects.nonNull(session.getAttribute("user"))) {
				if(Objects.isNull(cartService.getShoppingCart())) {
					User user = (User) session.getAttribute("user");
					ShoppingCart cart = new ShoppingCart();
					cart.setUser(user);
					this.cartService.setShoppingCart(user, cart);
					session.setAttribute("cart", cart);
				}
				this.cartService.addItemLine(itemLineRequest.getDescription(), itemLineRequest.getQuantity());
				return new ResponseEntity(HttpStatus.CREATED);

			}else{
				return new ResponseEntity(HttpStatus.LOCKED);
			}
		}catch (Exception e){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	/** to get the lines, if its send a parameter it will return all the specific line, else it will send all the lines
	 *
	 * @param request
	 * @return tHE ItemLines
	 */
	@RequestMapping(value = "/itemLine", method = RequestMethod.GET)
	public ResponseEntity<List<ItemLines>> find(HttpServletRequest request) throws Exception {
		List<ItemLines> myItemLines = new ArrayList<>();
		session = request.getSession();
		User user = (User) session.getAttribute("user");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "CartWebController");
		if(Objects.nonNull(user)){
			try {
				if (Objects.isNull(request.getParameter("description"))) {
					if (Objects.nonNull(cartService.getCart(user)))
						session.setAttribute("cart", cartService.getCart(user));
						myItemLines = this.cartService.findallLines((ShoppingCart) session.getAttribute("cart"));
				}else {
					String description = request.getParameter("description");
					if (Objects.nonNull(cartService.getCart(user)))
						myItemLines.add(this.cartService.findLineByDescription(user, description));
				}
			} catch (Exception e) {
				e.getMessage();
			}
		}else{
			throw new Exception("please, log you fist");
		}
		return ResponseEntity.accepted().headers(headers).body(myItemLines);
	}


	/** to find a specific item an delete its line. throws an exception if the description dont match any item in the repository
	 *
	 * @param description the name of the item
	 */
	@RequestMapping(value = "/itemLine/{description}", method = RequestMethod.DELETE)
	public ResponseEntity<ShoppingCart> deleteItem(HttpServletRequest request,
			@PathVariable("description") String description){
		session = request.getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "CartWebController");
		try {
			this.cartService.deleteItemByDescription((User) session.getAttribute("user"), description);
		}catch (Exception e){
			e.getMessage();
		}
		return ResponseEntity.accepted().headers(headers).body(this.cartService.getShoppingCart());
	}

	/** to change the quantity of an item in an itemLine
	 */
	@RequestMapping(value = "/itemLine", method = RequestMethod.PATCH)
	public ResponseEntity<ItemLines> updateItem(HttpServletRequest request,
			@RequestBody ItemLineRequest itemLineRequest) {
		session = request.getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "CartWebController");
		try {
			this.cartService.updateItemLine((User) session.getAttribute("user"),
					itemLineRequest.getDescription(), itemLineRequest.getQuantity());
		}catch (Exception e){
			e.getMessage();
		}
		return ResponseEntity.accepted().headers(headers)
				.body(this.cartService.findLineByDescription((User) session.getAttribute("user"), itemLineRequest.getDescription()));
	}

	/** to make the order and clear the shopping cart after return it
	 *
	 * @return the shopping cart
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ResponseEntity<ShoppingCart> buyCart(HttpServletRequest request) {
		session = request.getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "CartWebController");
		return ResponseEntity.accepted().headers(headers).body(this.cartService.buyCart((User) session.getAttribute("user")));
	}

	/** to obtain the price of a specific line of items
	 *
	 * @param description the name of the item
	 * @return double line price
	 */
	@RequestMapping(value ="/ItemLine/price/{description}", method = RequestMethod.GET)
	public ResponseEntity<Double> getLinePrice(HttpServletRequest request, @PathVariable("description") String description){
		session = request.getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "CartWebController");
		return ResponseEntity.accepted().headers(headers)
				.body(this.cartService.getLinePrice((User) this.session.getAttribute("user"), description));
	}

	/** to obtain the total price of the cart
	 *
	 * @return a double total price
	 */
	@RequestMapping(value = "/price", method = RequestMethod.GET)
	public ResponseEntity<Double> getTotalPrice(HttpServletRequest request){
		session = request.getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "CartWebController");
		return ResponseEntity.accepted().headers(headers)
				.body(this.cartService.getTotalPrice(cartService.getCart((User) session.getAttribute("user"))));
	}

	@RequestMapping(value = "{user}", method = RequestMethod.GET)
	public ResponseEntity<ShoppingCart> getMyCart(HttpServletRequest request){
		session = request.getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "CartWebController");
		this.cartService.getCart((User) session.getAttribute("user"));
		return ResponseEntity.accepted().headers(headers).body(this.cartService.getShoppingCart());
	}

}