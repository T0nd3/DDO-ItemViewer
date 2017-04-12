package view;

import java.util.ArrayList;
import java.util.List;

import enums.Type;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowController {

	public void start(Stage stage) {
		init(stage);

	}

	public void init(Stage stage) {
		stage = new Stage();
		BorderPane main = new BorderPane();
		stage.setScene(new Scene(main));
		main.setLeft(initLeftSide());
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
		;

		vBox.getChildren().add(searchField);
		vBox.getChildren().add(listView);
		return vBox;

	}

}
