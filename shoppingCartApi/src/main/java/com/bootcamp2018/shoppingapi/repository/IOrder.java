package com.bootcamp2018.shoppingapi.repository;

import com.bootcamp2018.shoppingapi.model.Order;
import com.bootcamp2018.shoppingapi.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrder extends JpaRepository<Order, Integer> {

	public Order findByShoppingCart(ShoppingCart shoppingCart);
}
