package com.bootcamp2018.shoppingapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "itemcategory")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_category")
	private long id;

	@Column(name = "category", unique = true)
	String categoryName;

	public Category(){}
	public Category(String categoryName){
		this.categoryName = categoryName;
	}

}
