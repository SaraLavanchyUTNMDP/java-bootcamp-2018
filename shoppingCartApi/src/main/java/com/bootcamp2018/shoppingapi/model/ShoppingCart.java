package com.bootcamp2018.shoppingapi.model;


import com.bootcampglobant.userregister.models.User;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jws.WebParam;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "carts")
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cart")
	private long id;

	@ManyToOne
	@JoinColumn(name="fk_iduser", referencedColumnName = "id_user", nullable = false)
	private User user;

    @Column(name = "date")
    private Date boughtDate;

	@Column(name = "totalprice")
    private double totalPrice;

	public ShoppingCart(){
		this.boughtDate = Calendar.getInstance().getTime();
		this.totalPrice = 0;
	}

	public ShoppingCart(User theUser) {
    	this.user = theUser;
        this.boughtDate = Calendar.getInstance().getTime();
    	this.totalPrice = 0;
    }



}
