package com.bootcamp2018.shoppingapi.controller;

import com.bootcamp2018.shoppingapi.model.Item;
import com.bootcamp2018.shoppingapi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
	@Autowired
	ItemService itemService;

	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestParam("description") String description, @RequestParam("price") Double price,
			@RequestParam("category") String category){
			itemService.addItem(description, price, category);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Item findItem(@RequestParam("description") String description){
		return itemService.getItem(description);
	}

	@RequestMapping(value="/category", method = RequestMethod.GET)
	public List<Item> findItemByCategory(@RequestParam("category") String category){
		return itemService.findByCategory(category);
	}

	@RequestMapping(method = RequestMethod.PATCH)
	public void changePrice(@RequestParam("price") double newPrice, @RequestParam("description") String description){
		itemService.updatePrice(newPrice,description);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteItem(@RequestParam("description") String description){
		itemService.deleteItem(description);
	}
}
