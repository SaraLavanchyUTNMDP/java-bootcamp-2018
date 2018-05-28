package com.bootcamp2018.shoppingapi.service;

import com.bootcamp2018.shoppingapi.model.Category;
import com.bootcamp2018.shoppingapi.repository.ICategoryRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Data
@Service
public class CategoryService {
	@Autowired
	private ICategoryRepository categoryRepository;

	public void addCategory(String category){
		if(Objects.isNull(categoryRepository.findByCategoryName(category)))
			categoryRepository.save(new Category(category));
	}

	public Category getCategory(String category){
		return categoryRepository.findByCategoryName(category);
	}

	public void deleteCategory(String category){
		categoryRepository.delete(categoryRepository.findByCategoryName(category));
	}
}
