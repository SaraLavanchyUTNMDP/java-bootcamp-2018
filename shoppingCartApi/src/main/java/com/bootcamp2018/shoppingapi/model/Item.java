package com.bootcamp2018.shoppingapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_item")
	private long id;

	@Column(name = "description", nullable = false, unique = true)
    private String description;

	@Column(name = "price", nullable = false)
    private Double price;

	@ManyToOne
	@JoinColumn(name="fk_idcategory", referencedColumnName = "id_category", nullable = false)
	private Category category;

	public Item(){}
    public Item(String item, double thePrice, Category category) {
        this.description = item;
        this.price = thePrice;
        this.category = category;
    }


    /** Compare two Items and return true if the items have the same description
     *
      * @param o the item to compare
     * @return true if the items have the same description and false if not.
     */
    @Override
    public boolean equals(Object o) {
        boolean areEquals = false;
        if(!Objects.isNull(o) && o instanceof Item){
            if((((Item) o).description.equals(this.description))){
                areEquals =true;
            }
        }
        return areEquals;
    }

    @Override
    public int hashCode() {

        return Objects.hash(description, price);
    }

}
