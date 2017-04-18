package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ArchievmentReader implements Runnable {
	int from;
	int to;

	String name;
	File file = new File("badge.txt");
	String line;

	public ArchievmentReader(String name, int from, int to) {
		super();
		this.name = name;
		this.from = from;
		this.to = to;
		System.err.println(toString());
	}

	@Override
	public String toString() {
		return "ArchievmentReader [from=" + from + ", to=" + to + ", name=" + name + "]";
	}

	public void read(int from, int to) throws IOException {

		FileWriter fileWriter = new FileWriter(file, true);
		for (; from < to; from++) {
			if (from % 50 == 0)
				System.err.println(name + "@Profil: " + from);
			try {
				Document doc = Jsoup.connect("https://www.sololearn.com/Profile/" + from + "/").get();
				int counter = 71;
				Elements select = doc.getElementsByClass("userAchievements");
				Elements select2 = select.select("div#achievement" + counter);
				if (select2.hasClass("achievement disabled") == false) {
					fileWriter.append("\n     https://www.sololearn.com/Profile/" + from + "/");
					System.err.println("https://www.sololearn.com/Profile/" + from + "/");
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
