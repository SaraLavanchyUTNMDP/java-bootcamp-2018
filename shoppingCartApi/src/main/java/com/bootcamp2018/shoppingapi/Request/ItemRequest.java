package com.bootcamp2018.shoppingapi.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemRequest {
	@JsonProperty("description")
	String description;
	@JsonProperty("price")
	Double price;
	@JsonProperty("Category")
	String category;
}
