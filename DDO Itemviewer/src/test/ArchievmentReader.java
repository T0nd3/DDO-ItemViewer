package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ArchievmentReader implements Runnable

{
	public void setTo(int to) {
		this.to = to;
	}

	SimpleIntegerProperty from = new SimpleIntegerProperty();
	int to;

	File file = new File("badge.txt");

	public ArchievmentReader(int from, int to) {
		super();
		this.from.set(from);
		this.to = to;
		System.err.println(toString());
	}

	@Override
	public String toString() {
		return "ArchievmentReader [from=" + from + ", to=" + to + "]";
	}

	public void read(IntegerProperty from, int to) throws IOException {
		FileWriter fileWriter = new FileWriter(file, true);
		for (; from.getValue().intValue() < to; from.set((from.get() + 1))) {
			if (from.get() % 50 == 0)
				System.err.println("@Profil: " + from.get());
			try {
				Document doc = Jsoup.connect("https://www.sololearn.com/Profile/" + from.get() + "/").get();
				int counter = 71;
				Elements select = doc.getElementsByClass("userAchievements");
				Elements select2 = select.select("div#achievement" + counter);
				if (select2.hasClass("achievement disabled") == false) {
					fileWriter.append("\n     https://www.sololearn.com/Profile/" + from.get() + "/");
					System.err.println("https://www.sololearn.com/Profile/" + from.get() + "/");
					fileWriter.flush();
				}
			} catch (HttpStatusException e) {
			}
		}
		fileWriter.close();
	}

	public void run() {
		try {
			read(from, to);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
