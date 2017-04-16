package beans;

public class ProductOf {

	private int counter;
	private Item item;

	public ProductOf() {
	}

	public ProductOf(int counter, Item item) {
		super();
		this.counter = counter;
		this.item = item;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	// private HashMap<Integer, Item> numberToItem;
	// private HashMap<Item, Integer> itemToNumber;
	//
	// public ProductOf(HashMap<Integer, Item> numberToItem, HashMap<Item,
	// Integer> itemToNumber) {
	// super();
	// this.numberToItem = numberToItem;
	// this.itemToNumber = itemToNumber;
	// }
	//
	// public HashMap<Integer, Item> getNumberToItem() {
	// return numberToItem;
	// }
	//
	// public void setNumberToItem(HashMap<Integer, Item> numberToItem) {
	// this.numberToItem = numberToItem;
	// }
	//
	// public HashMap<Item, Integer> getItemToNumber() {
	// return itemToNumber;
	// }
	//
	// public void setItemToNumber(HashMap<Item, Integer> itemToNumber) {
	// this.itemToNumber = itemToNumber;
	// }
	//
	// public void addTo(int counter, Item item) {
	// numberToItem.put(counter, item);
	// itemToNumber.put(item, counter);
	// }
	//
	// public Item (int counter){
	//
	// }

}
