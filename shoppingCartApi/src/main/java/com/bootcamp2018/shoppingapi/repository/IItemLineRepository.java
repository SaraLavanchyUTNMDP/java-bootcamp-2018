package com.bootcamp2018.shoppingapi.repository;

import com.bootcamp2018.shoppingapi.model.Item;
import com.bootcamp2018.shoppingapi.model.ItemLines;
import com.bootcamp2018.shoppingapi.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IItemLineRepository extends JpaRepository<ItemLines, Integer> {
	public List<ItemLines> findByMyItem(Item anItem);
	public List<ItemLines> findByShoppingCart(ShoppingCart shoppingCart);

	@Transactional
	@Modifying
	public void deleteByMyItemAndShoppingCart(Item myItem, ShoppingCart shoppingCart);

	@Transactional
	@Modifying
	@Query("update ItemLines set quantity = :newquantity, linePrice = :newLinePrice  where fk_iditem = :id_item and fk_idcart = :idcart")
	public void updateByItem(@Param("id_item")long idItem, @Param("newquantity") int quantity,
			@Param("idcart") long idcart, @Param("newLinePrice") double newLinePrice);
}
