package com.bootcamp2018.shoppingapi.controller;

import com.bootcamp2018.shoppingapi.Request.ItemRequest;
import com.bootcamp2018.shoppingapi.model.Item;
import com.bootcamp2018.shoppingapi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
	@Autowired
	ItemService itemService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity add(@RequestBody ItemRequest request){
		try {
			itemService.addItem(request.getDescription(), request.getPrice(), request.getCategory());
			return new ResponseEntity(HttpStatus.CREATED);
		} catch (Exception e){
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="{description}", method = RequestMethod.GET)
	public ResponseEntity<Item> findItem(HttpServletRequest request, @PathVariable("description") String description){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "ItemController");
		Item item = itemService.getItem(description);
		return ResponseEntity.accepted().headers(headers).body(item);
	}

	@RequestMapping(value="/category/{category}", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> findItemByCategory(HttpServletRequest request, @PathVariable("category") String category){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "ItemController");
		List<Item> items = itemService.findByCategory(category);
		return ResponseEntity.accepted().headers(headers).body(items);
	}

	@RequestMapping(value="{description}/{price}", method = RequestMethod.PATCH)
	public ResponseEntity changePrice(@PathVariable("price") double newPrice, @PathVariable("description") String description){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "ItemController");
		itemService.updatePrice(newPrice,description);
		return ResponseEntity.accepted().headers(headers).body(itemService.getItem(description));
	}

	@RequestMapping(value= "{description}", method = RequestMethod.DELETE)
	public ResponseEntity deleteItem(@PathVariable("description") String description){
		try {
			itemService.deleteItem(description);
			return new ResponseEntity(HttpStatus.ACCEPTED);
		}catch (Exception e){
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
