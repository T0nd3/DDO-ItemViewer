package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FileLoader {

	public void loadPic(String spec, String fileName) throws IOException {
		URL url = new URL(spec);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = new FileOutputStream("src//resources//" + fileName);
		inputStream = connection.getInputStream();
		int read = -1;
		byte[] buffer = new byte[4096];
		while ((read = inputStream.read(buffer)) != -1) {
			fileOutputStream.write(buffer, 0, read);
		}
		inputStream.close();
		fileOutputStream.close();
	}

	public List<String> loadInfos(URL url) throws IOException {
		List<String> finalList = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		Document doc = Jsoup.connect(url.toString()).get();
		getItemTableInfo(result, doc);
		getItemDesciption(result, doc);
		getItemInfo2(result, doc);

		for (String s : result) {
			String replace = s.replaceAll("[^\\x20-\\x7e]", "");
			String replaceAll = replace.replaceAll("-", "");
			finalList.add(replaceAll);
		}
		return finalList;
	}

	private void getItemTableInfo(List<String> result, Document doc) throws IOException {
		Elements table = doc.select("table");
		for (Element row : table.select("tr")) {
			Elements tds = row.select("td");
			Elements img = tds.select("img");
			String string = img.toString();
			String[] split = string.split("\"");
			if (split.length > 1) {
				loadPic(split[1], split[3]);
				result.add(split[3]);
			}
			result.add(tds.text());
		}

	}

	private void getItemDesciption(List<String> result, Document doc) {
		Elements desc = doc.select("div#page-content");
		for (Element element : desc.select("p")) {
			result.add(element.text());
		}
	}

	private void getItemInfo2(List<String> result, Document doc) {
		Elements found = doc.select("div#page-content");
		for (Element ul : found.select("ul")) {
			for (Element li : ul.select("li"))
				result.add(li.text());
		}
	}

}
