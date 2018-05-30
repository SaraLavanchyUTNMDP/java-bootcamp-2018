package com.bootcamp2018.shoppingapi.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemLineRequest {
	@JsonProperty("description")
	String description;
	@JsonProperty("quantity")
	int quantity;
}
