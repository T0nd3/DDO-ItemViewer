package test;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Coordinater {

	int start = 1039999;
	int increment = 9999;
	int END = 1500000;
	int size = 4;
	List<Thread> threads;
	ObservableList<ViewElement> observableArrayList;

	public void work() {

		threads = new ArrayList<Thread>();
		while (start <= END) {

			threads.removeIf(new Predicate<Thread>() {

				@Override
				public boolean test(Thread t) {
					if (t.getState().equals(State.TERMINATED)) {
						return true;
					}
					return false;
				}
			});
			if (threads.size() < size) {
				ArchievmentReader reader = new ArchievmentReader(start = (start + 1), start = (start + increment));
				Thread thread = new Thread(reader);
				threads.add(thread);
				thread.start();
			}

		}
	}

	public void initView(Stage stage) {
		stage = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		ListView<ViewElement> listView = new ListView<ViewElement>();
		root.setCenter(listView);
		observableArrayList = FXCollections.observableArrayList();

		listView.setItems(observableArrayList);
		stage.setScene(scene);
		stage.show();
		threads = new ArrayList<Thread>();
		Thread i = new Thread(new Runnable() {

			@Override
			public void run() {

				while (start < END) {
					threads.removeIf(new Predicate<Thread>() {

						@Override
						public boolean test(Thread t) {
							if (t.getState().equals(State.TERMINATED)) {
								return true;
							}
							return false;
						}
					});
					if (threads.size() < size) {
						final ViewElement element = new ViewElement(new ProgressBar(),
								new ArchievmentReader(start = (start + 1), start = (start + increment)),
								new Label("Reader" + start + " - " + (start + increment)));
						Thread thread = new Thread(element.getReader());
						threads.add(thread);
						Platform.runLater(new Runnable() {

							@Override
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
