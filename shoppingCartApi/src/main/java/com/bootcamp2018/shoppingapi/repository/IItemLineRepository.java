package com.bootcamp2018.shoppingapi.repository;

import com.bootcamp2018.shoppingapi.model.Item;
import com.bootcamp2018.shoppingapi.model.ItemLines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IItemLineRepository extends JpaRepository<ItemLines, Integer> {
	public ItemLines findByMyItem(Item anItem);

	@Transactional
	@Modifying
	public void deleteByMyItem(Item anItem);

	@Transactional
	@Modifying
	@Query("update ItemLines set quantity = :newquantity where fk_iditem = :id_item")
	public void updateByItem(@Param("id_item")long idItem, @Param("newquantity") int quantity);
}
