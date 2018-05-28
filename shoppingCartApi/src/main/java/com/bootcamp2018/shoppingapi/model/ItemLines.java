package com.bootcamp2018.shoppingapi.model;

import lombok.Data;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@Data
@Table(name = "itemlines")
public class ItemLines {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_itemline")
	private long id;

	@ManyToOne
	@JoinColumn(name="fk_iditem", referencedColumnName = "id_item", nullable = false)
    private Item myItem;

	@ManyToOne
	@JoinColumn(name="fk_idcart", referencedColumnName = "id_cart", nullable = false)
	private ShoppingCart shoppingCart;

	@Column(name = "quantity")
    private int Quantity;

	@Column(name = "price")
	private double linePrice;

    public ItemLines(){}
    public ItemLines(Item item, int quantities, ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    	this.myItem = item;
        this.Quantity = quantities;
        this.getLinePrice();
    }

    /** Compare if the Item of this line is the same of @param o Item
     *
     * @param o the item that we want to compare. It has to be an instance of Item.
     * @return true if @param o is the item that the line contains.
     */
    @Override
    public boolean equals(Object o) {
        boolean areEquals = false;
        if(!Objects.isNull(o) && o instanceof Item){
            if((o.equals(this.myItem))){
                areEquals =true;
            }
        }
        return areEquals;
    }

    @Override
    public int hashCode() {

        return Objects.hash(myItem, Quantity);
    }

    public double getLinePrice() {
        this.linePrice = this.Quantity*this.getMyItem().getPrice();
        return this.linePrice;
    }
}
