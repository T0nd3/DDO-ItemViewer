package module;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

public class ViewElement extends HBox {

	private ProgressBar bar;
	private ArchievmentReader reader;
	private Label label;
	SimpleIntegerProperty integerProperty = new SimpleIntegerProperty();
	private double d;
	private int increment;

	public ViewElement(ProgressBar bar, ArchievmentReader reader, Label label, int increment) {
		this.setSpacing(5);
		this.bar = bar;
		this.reader = reader;
		this.label = label;
		this.integerProperty = this.reader.from;
		this.bar.setMinWidth(100);
		this.increment = increment;
		setElements();
		calc();
	}

	public ProgressBar getBar() {
		return bar;
	}

	public void setBar(ProgressBar bar) {
		this.bar = bar;
	}

	public ArchievmentReader getReader() {
		return reader;
	}

	public void setReader(ArchievmentReader reader) {
		this.reader = reader;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public void setElements() {
		this.getChildren().add(label);
		this.getChildren().add(bar);
	}

	void calc() {
		reader.from.addListener(new ChangeListener<Number>() {

			// @Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				d = ((double) reader.to) - newValue.doubleValue();
				Platform.runLater(new Runnable() {

					// @Override
					public void run() {
						bar.setProgress(((increment / d) / 10));
						if (bar.getProgress() >= 1.0d || bar.getProgress() >= 0.98d) {
							bar.setStyle("-fx-accent: green;");
						}
					}
				});

			}
		});

	}
}
