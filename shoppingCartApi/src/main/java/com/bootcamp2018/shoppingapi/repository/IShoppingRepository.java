package com.bootcamp2018.shoppingapi.repository;
import com.bootcamp2018.shoppingapi.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IShoppingRepository extends JpaRepository<ShoppingCart,Integer> {

	@Transactional
	@Modifying
	@Query("update ShoppingCart set totalprice = :totalprice  where id_cart = :idcart")
	public void savePrice(@Param("totalprice") double totalprice, @Param("idcart") long idCart);
}
