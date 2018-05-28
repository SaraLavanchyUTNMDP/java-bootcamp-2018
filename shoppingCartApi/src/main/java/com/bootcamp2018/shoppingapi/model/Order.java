package com.bootcamp2018.shoppingapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id_order")
		private long id;

		@ManyToOne
		@JoinColumn(name="fk_idcart", referencedColumnName = "id_cart", nullable = false)
		private ShoppingCart shoppingCart;

		@Column(name = "date")
		private Date date;

		@Column(name = "finalprice")
		public double finalprice;
	public Order(){}
	public Order(ShoppingCart mycart, Date time, double finalprice) {
		this.shoppingCart = mycart;
		this.date=time;
		this.finalprice=finalprice;
	}
}
