package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ArchievmentReader {

	public void read() throws IOException {
		int max = 15000000;
		int profilecounter = 43900;
		int userCounter = 1;
		FileWriter fileWriter = new FileWriter(new File("badge.txt"));
		for (; profilecounter < max; profilecounter++) {
			if (profilecounter % 50 == 0)
				System.err.println("Profil: " + profilecounter);
			try {
				Document doc = Jsoup.connect("https://www.sololearn.com/Profile/" + profilecounter + "/")
						.timeout(999999).get();
				int counter = 71;
				Elements select = doc.getElementsByClass("userAchievements");
				Elements select2 = select.select("div#achievement" + counter);
				if (select2.hasClass("achievement disabled") == false) {
					fileWriter.append(
							"\n " + userCounter + ":    https://www.sololearn.com/Profile/" + profilecounter + "/");
					System.err.println("https://www.sololearn.com/Profile/" + profilecounter + "/");
					userCounter++;
					fileWriter.flush();
				}
			} catch (HttpStatusException e) {
			}

		}
		System.err.println(userCounter);
		fileWriter.close();
	}
}
