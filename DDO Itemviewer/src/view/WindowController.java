package view;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import beans.Item;
import beans.ProductOf;
import enums.MaterialTypes;
import enums.Type;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowController {
	TableView<Item> tableView;
	TextField searchField;
	private ObservableList<Item> toAddList = FXCollections.observableArrayList(testItems());
	private ObservableList<Item> showList = FXCollections.observableArrayList();

	public List<Item> testItems() {
		List<Item> list = new ArrayList<Item>();
		Item bronzeOre = new Item(1234, "Bronze Ore", "blackore.png", "Material used for crafting bronze products.",
				new ArrayList<>(), 7, 0, Type.MATERIALS, MaterialTypes.ORE, null);
		bronzeOre.getLocations().add("Hidell Plains");
		Item smallHorn = new Item(4421, "Small Horn", "smallhorn.png",
				"A horn extracted from a goblin. Used in crafting.", new ArrayList<>(), 10, 0, Type.MATERIALS,
				MaterialTypes.HORN, null);
		smallHorn.getLocations().add("Goblin");
		Item bronzeIngot = new Item(1332, "Bronze Ingot", "blueingot.png",
				"Processed from bronze ore, used for crafting.", new ArrayList<>(), 10, 60, Type.MATERIALS,
				MaterialTypes.METALINGOT, new ArrayList());
		bronzeIngot.getProductOfList().add(new ProductOf(2, bronzeOre));
		bronzeIngot.getProductOfList().add(new ProductOf(1, smallHorn));

		list.add(bronzeIngot);
		list.add(bronzeOre);
		list.add(smallHorn);
		return list;

	}

	public void start(Stage stage) {
		init(stage);

	}

	public void init(Stage stage) {
		stage = new Stage();
		BorderPane main = new BorderPane();
		stage.setScene(new Scene(main));
		main.setLeft(initLeftSide());
		main.setCenter(initCenter());
		stage.show();
	}

	public void showAll() {
		System.err.println("reset");
		tableView.setItems(FXCollections.observableArrayList(testItems()));
	}

	public void filter(Type filter) {
		ObservableList<Item> items = toAddList;
		if (items != null) {
			if (!filter.equals(Type.ALL)) {
				items.filtered(new Predicate<Item>() {

					@Override
					public boolean test(Item t) {
						if (t.getType().equals(filter)) {
							if (!showList.contains(t)) {
								showList.add(t);
							}
						} else {
							showList.remove(t);
						}
						return false;
					}
				});
			} else if (filter.equals(Type.ALL)) {
				for (Item item : toAddList) {
					if (!showList.contains(item)) {
						showList.add(item);
					}
				}
			}

			tableView.setItems(showList);
		}
	}

	public void search(String search) {
		ObservableList<Item> items = toAddList;
		if (items != null) {
			items.filtered(new Predicate<Item>() {

				@Override
				public boolean test(Item t) {
					if (t.getName().contains(search)) {
						if (!showList.contains(t)) {
							showList.add(t);
						}
					} else {
						showList.remove(t);
					}
					return false;
				}
			});
		}
		tableView.setItems(showList);
	}

	public Pane initLeftSide() {
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		searchField = new TextField();

		searchField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					search(newValue);
				} else {
					showAll();
				}
			}
		});
		hBox.getChildren().add(searchField);
		ListView<Type> listView = new ListView<Type>();
		Type[] values = Type.values();
		List<Type> list = new ArrayList<Type>();
		for (Type type : values) {
			list.add(type);
		}
		listView.setItems(FXCollections.observableList(list));
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Type>() {

			public void changed(ObservableValue<? extends Type> observable, Type oldValue, Type newValue) {
				filter(newValue);
			}
		});
		vBox.getChildren().add(hBox);
		vBox.getChildren().add(listView);
		return vBox;

	}

	public TableView<Item> initCenter() {
		tableView = new TableView<Item>();
		TableColumn<Item, String> numberColumn = new TableColumn<Item, String>("ITEM-Nummer");
		numberColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("number"));
		TableColumn<Item, String> nameColumn = new TableColumn<Item, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
		TableColumn<Item, Image> iconColumn = new TableColumn<Item, Image>("PIC");
		iconColumn.setCellValueFactory(new PropertyValueFactory<Item, Image>("picture"));
		TableColumn<Item, String> descriptionColumn = new TableColumn<Item, String>("Description");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
		TableColumn<Item, String> locationColumn = new TableColumn<Item, String>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("location"));
		TableColumn<Item, Integer> goldValueColumn = new TableColumn<Item, Integer>("Gold Value");
		goldValueColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("goldValue"));
		TableColumn<Item, Integer> forgeCostColumn = new TableColumn<Item, Integer>("Forgecost");
		forgeCostColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("forgeCost"));
		TableColumn<Item, String> typeColumn = new TableColumn<Item, String>("Type");
		typeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Type"));
		TableColumn<Item, String> subTypeColumn = new TableColumn<Item, String>("SubType");
		subTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("SubType"));
		TableColumn<Item, String> productOfColumn = new TableColumn<Item, String>("ProductOf");

		tableView.getColumns().add(iconColumn);
		tableView.getColumns().add(nameColumn);
		tableView.getColumns().add(numberColumn);
		tableView.getColumns().add(descriptionColumn);
		tableView.getColumns().add(locationColumn);
		tableView.getColumns().add(goldValueColumn);
		tableView.getColumns().add(forgeCostColumn);
		tableView.getColumns().add(typeColumn);
		tableView.getColumns().add(subTypeColumn);
		tableView.getColumns().add(productOfColumn);
		tableView.setItems(FXCollections.observableList(testItems()));
		return tableView;

	}

}
