package module;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Coordinater {

	int start;
	// = 1039999;
	int increment;
	// = 10000;
	int END;
	// = 1500000;
	int size = 4;
	List<Thread> threads;
	ObservableList<ViewElement> observableArrayList;

	public void initView(Stage stage) {
		stage = new Stage();
		BorderPane root = new BorderPane();
		root.setPrefSize(500, 300);
		Scene scene = new Scene(root);
		HBox box = new HBox();
		final TextField fieldStart = new TextField();
		fieldStart.setPromptText("Start @");
		final TextField fieldEND = new TextField();
		fieldEND.setPromptText("END @");
		final TextField fieldIncrement = new TextField();
		fieldIncrement.setPromptText("increment");
		Button starter = new Button("Start");
		box.getChildren().add(fieldStart);
		box.getChildren().add(fieldEND);
		box.getChildren().add(fieldIncrement);
		box.getChildren().add(starter);
		box.setSpacing(2);
		root.setTop(box);
		starter.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if (!fieldStart.getText().isEmpty()) {
					start = Integer.valueOf(fieldStart.getText());
				}
				if (!fieldIncrement.getText().isEmpty()) {
					increment = Integer.valueOf(fieldIncrement.getText());
				}
				if (!fieldEND.getText().isEmpty()) {
					END = Integer.valueOf(fieldEND.getText());
				}

				doWork();
			}
		});
		ListView<ViewElement> listView = new ListView<ViewElement>();
		root.setCenter(listView);
		observableArrayList = FXCollections.observableArrayList();

		listView.setItems(observableArrayList);
		stage.setScene(scene);
		stage.show();

		// doWork();
	}

	private void doWork() {
		threads = new ArrayList<Thread>();
		Thread i = new Thread(new Runnable() {

			// @Override
			public void run() {

				while (start < END) {
					threads.removeIf(new Predicate<Thread>() {

						// @Override
						public boolean test(Thread t) {
							if (t.getState().equals(State.TERMINATED)) {
								return true;
							}
							return false;
						}
					});
					if (threads.size() < size) {
						final ViewElement element = new ViewElement(new ProgressBar(),
								new ArchievmentReader(start = (start), start = (start + increment)),
								new Label("Reader: " + (start - increment) + " - " + (start)), increment);
						Thread thread = new Thread(element.getReader());
						threads.add(thread);
						Platform.runLater(new Runnable() {

							// @Override
							public void run() {
								observableArrayList.add(element);
							}
						});

						thread.start();

					}

				}

			}
		});
		i.start();
	}
}
