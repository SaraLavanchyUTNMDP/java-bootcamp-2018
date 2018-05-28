package com.bootcamp2018.shoppingapi.service;

import com.bootcamp2018.shoppingapi.model.Item;
import com.bootcamp2018.shoppingapi.repository.IItemLineRepository;
import com.bootcamp2018.shoppingapi.repository.IItemRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Data
@Service
public class ItemService {
	@Autowired
	private IItemRepository itemRepository;
	@Autowired
	private CategoryService categoryService;

	public void addItem(String description, double price, String category){
		if(Objects.isNull(itemRepository.findByDescription(description)))
			if (Objects.isNull(categoryService.getCategory(category)))
				categoryService.addCategory(category);
			itemRepository.save(new Item(description, price, categoryService.getCategory(category)));
	}

	public Item getItem(String description){
		return itemRepository.findByDescription(description);
	}

	public void updatePrice(double newPrice, String description){
		itemRepository.updateItemPrice(newPrice, description);
	}

	public void deleteItem(String description){
		itemRepository.delete(itemRepository.findByDescription(description));
	}

	public List<Item> findByCategory(String category){
		return itemRepository.findAllByCategory(categoryService.getCategory(category));
	}
}
