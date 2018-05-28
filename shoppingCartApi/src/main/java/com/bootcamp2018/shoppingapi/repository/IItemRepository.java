package com.bootcamp2018.shoppingapi.repository;

import com.bootcamp2018.shoppingapi.model.Category;
import com.bootcamp2018.shoppingapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IItemRepository extends JpaRepository<Item, Integer> {
	public Item findByDescription(String description);

	public List<Item> findAllByCategory(Category category);

	@Transactional
	@Modifying
	@Query("update Item set price = :itemPrice where description = :description")
	public void updateItemPrice(@Param("itemPrice") double itemPrice, @Param("description") String description);
}
