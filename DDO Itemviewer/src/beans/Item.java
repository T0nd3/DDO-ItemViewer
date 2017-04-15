package beans;

import java.util.List;

import javax.persistence.Entity;

import enums.Type;

@Entity
public class Item {

	private int number;
	private String name;
	private String picture;
	private String description;
	private String location;
	private int goldValue;
	private int forgeCost;
	private Type type;
	private Subtype subType;

	private List<ProductOf> productOfList;

	public Item() {

	}

	public Item(int number, String name, String picture, String description, String location, int goldValue,
			int forgeCost, Type type, Subtype subType, List<ProductOf> productOfList) {
		super();
		this.number = number;
		this.name = name;
		this.picture = picture;
		this.description = description;
		this.location = location;
		this.goldValue = goldValue;
		this.forgeCost = forgeCost;
		this.type = type;
		this.subType = subType;
		this.productOfList = productOfList;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getGoldValue() {
		return goldValue;
	}

	public void setGoldValue(int goldValue) {
		this.goldValue = goldValue;
	}

	public int getForgeCost() {
		return forgeCost;
	}

	public void setForgeCost(int forgeCost) {
		this.forgeCost = forgeCost;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Subtype getSubType() {
		return subType;
	}

	public void setSubType(Subtype subType) {
		this.subType = subType;
	}

	public List<ProductOf> getProductOfList() {
		return productOfList;
	}

	public void setProductOfList(List<ProductOf> productOfList) {
		this.productOfList = productOfList;
	}

}
