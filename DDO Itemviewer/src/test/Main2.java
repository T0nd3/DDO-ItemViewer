package test;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main2 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Coordinater coordinater = new Coordinater();
		coordinater.initView(primaryStage);
	}

}
