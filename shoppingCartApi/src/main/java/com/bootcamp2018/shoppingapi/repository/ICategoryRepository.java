package com.bootcamp2018.shoppingapi.repository;

import com.bootcamp2018.shoppingapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
	public Category findByCategoryName(String category);
}
