package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beans.Item;
import enums.MaterialTypes;
import enums.Type;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowController {

	public List<Item> testItems() {
		List<Item> list = new ArrayList<Item>();
		Item bronzeOre = new Item(1234, "Bronze Ore", "blackore.png", "Material used for crafting bronze products.",
				"Hidell Plains", 7, 0, Type.MATERIALS, MaterialTypes.ORE, null);
		Item smallHorn = new Item(4421, "Small Horn", "smallhorn.png",
				"A horn extracted from a goblin. Used in crafting.", "Goblin", 10, 0, Type.MATERIALS,
				MaterialTypes.HORN, null);
		Item bronzeIngot = new Item(1332, "Bronze Ingot", "blueingot.png",
				"Processed from bronze ore, used for crafting.", "", 10, 60, Type.MATERIALS, MaterialTypes.METALINGOT,
				new HashMap<Item, Integer>());
		bronzeIngot.getProductOf().put(bronzeOre, 2);
		bronzeIngot.getProductOf().put(smallHorn, 1);

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

	public Pane initLeftSide() {
		VBox vBox = new VBox();
		TextField searchField = new TextField();
		searchField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {
				System.err.println(event.getCode());
			}

		});
		ListView<Type> listView = new ListView<Type>();
		Type[] values = Type.values();
		List<Type> list = new ArrayList<Type>();
		for (Type type : values) {
			list.add(type);
		}
		listView.setItems(FXCollections.observableList(list));
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Type>() {

			public void changed(ObservableValue<? extends Type> observable, Type oldValue, Type newValue) {
				System.err.println(newValue);
			}
		});
		vBox.getChildren().add(searchField);
		vBox.getChildren().add(listView);
		return vBox;

	}

	public TableView<Item> initCenter() {
		TableView<Item> tableView = new TableView<Item>();

		TableColumn<Item, String> numberColumn = new TableColumn<Item, String>();
		numberColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("number"));
		TableColumn<Item, String> nameColumn = new TableColumn<Item, String>();
		nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
		TableColumn<Item, Image> iconColumn = new TableColumn<Item, Image>();
		iconColumn.setCellValueFactory(new PropertyValueFactory<Item, Image>("Photo"));
		TableColumn<Item, String> descriptionColumn = new TableColumn<Item, String>();
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
		TableColumn<Item, String> locationColumn = new TableColumn<Item, String>();
		locationColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("location"));
		TableColumn<Item, String> goldValueColumn = new TableColumn<Item, String>();
		goldValueColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Gold Value"));
		TableColumn<Item, String> forgeCostColumn = new TableColumn<Item, String>();
		forgeCostColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Forgecost"));
		TableColumn<Item, String> typeColumn = new TableColumn<Item, String>();
		typeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Type"));
		TableColumn<Item, String> subTypeColumn = new TableColumn<Item, String>();
		subTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("SubType"));
		TableColumn<Item, String> productOfColumn = new TableColumn<Item, String>();
		productOfColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("ProductOf"));

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
