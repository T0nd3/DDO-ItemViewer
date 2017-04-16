package view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Item;
import beans.ProductOf;
import enums.Type;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import test.FileLoader;
import utils.Mapper;

public class WindowController {
	private TableView<Item> tableView;
	private TextField searchField;
	private ObservableList<Item> toAddList;
	private ObservableList<Item> showList = FXCollections.observableArrayList();
	private ObjectMapper mapper;
	private FileLoader loader;
	private List<URL> URLS = new ArrayList<URL>();

	// public List<Item> testItems() {
	// List<Item> list = new ArrayList<Item>();
	// Item bronzeOre = new Item("Bronze Ore", "blackore.png", "Material used
	// for crafting bronze products.",
	// new ArrayList<String>(), 7, 0, Type.MATERIALS, new
	// ArrayList<ProductOf>());
	// bronzeOre.getLocations().add("Hidell Plains");
	// Item smallHorn = new Item("Small Horn", "smallhorn.png", "A horn
	// extracted from a goblin. Used in crafting.",
	// new ArrayList<String>(), 10, 0, Type.MATERIALS, new
	// ArrayList<ProductOf>());
	// smallHorn.getLocations().add("Goblin");
	// Item bronzeIngot = new Item("Bronze Ingot", "blueingot.png", "Processed
	// from bronze ore, used for crafting.",
	// new ArrayList<String>(), 10, 60, Type.MATERIALS, new
	// ArrayList<ProductOf>());
	// bronzeIngot.getProductOfList().add(new ProductOf(2, bronzeOre));
	// bronzeIngot.getProductOfList().add(new ProductOf(1, smallHorn));
	//
	// list.add(bronzeIngot);
	// list.add(bronzeOre);
	// list.add(smallHorn);
	// return list;
	//
	// }

	public void start(Stage stage) throws MalformedURLException {
		init(stage);

	}

	public void getURLS() throws IOException {
		URL urlmain = new URL("http://ddon.wikidot.com/ore:home");
		Document doc = Jsoup.connect(urlmain.toString()).get();
		Elements tables = doc.select("table");
		for (Element row : tables.select("tr")) {
			Elements td = row.select("td");
			Elements select = td.select("a");
			String string = select.toString();

			String[] split = string.split("\"");
			if (split.length > 1) {
				URLS.add(new URL("http://ddon.wikidot.com" + split[1]));
			}

		}

	}

	public List<Item> loadData() {
		TypeReference<List<Item>> typeReference = new TypeReference<List<Item>>() {
		};
		ArrayList<Item> l = new ArrayList<Item>();
		try {
			Object readValue = mapper.readValue(new File("test.json"), typeReference);
			l = (ArrayList<Item>) readValue;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return l;
	}

	public void init(Stage stage) throws MalformedURLException {
		mapper = Mapper.getMapper();
		loader = new FileLoader();
		try {
			getURLS();
		} catch (IOException e) {
			e.printStackTrace();
		}
		toAddList = FXCollections.observableArrayList(loadData());
		stage = new Stage();
		BorderPane main = new BorderPane();
		stage.setScene(new Scene(main));
		main.setLeft(initLeftSide());
		main.setCenter(initCenter());
		stage.show();
	}

	public void showAll() {
		tableView.setItems(toAddList);
	}

	public void filter(final Type filter) {
		ObservableList<Item> items = toAddList;
		if (items != null) {
			if (!filter.equals(Type.ALL)) {
				items.filtered(new Predicate<Item>() {

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

	public void search(final String search) {
		ObservableList<Item> items = toAddList;
		if (items != null) {
			items.filtered(new Predicate<Item>() {

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
		Button v = new Button();
		v.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				List<Item> toPersist = new ArrayList<Item>();
				for (URL url : URLS) {
					try {
						List<String> loadInfos = loader.loadInfos(url);
						String string = loadInfos.get(4);
						String replace = string.replace(" G", "");
						Item item = new Item(loadInfos.get(2), loadInfos.get(1), loadInfos.get(6),
								new ArrayList<String>(), Integer.parseInt(replace), Integer.parseInt(loadInfos.get(3)),
								Type.MATERIALS, new ArrayList<ProductOf>());
						for (int x = 7; x < loadInfos.size(); x++) {
							item.getLocations().add(loadInfos.get(x));
						}
						toPersist.add(item);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					mapper.writeValue(new File("test.json"), toPersist);
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		searchField = new TextField();
		searchField.setPromptText("Itemname");
		searchField.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					search(newValue);
				} else {
					showAll();
				}
			}
		});

		hBox.getChildren().add(searchField);
		hBox.getChildren().add(v);
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
		TableColumn<Item, String> productOfColumn = new TableColumn<Item, String>("ProductOf");

		tableView.getColumns().add(iconColumn);
		tableView.getColumns().add(nameColumn);
		tableView.getColumns().add(descriptionColumn);
		tableView.getColumns().add(locationColumn);
		tableView.getColumns().add(goldValueColumn);
		tableView.getColumns().add(forgeCostColumn);
		tableView.getColumns().add(typeColumn);
		tableView.getColumns().add(productOfColumn);
		tableView.setItems(toAddList);
		return tableView;

	}

}
