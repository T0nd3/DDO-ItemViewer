package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FileLoader {
	Map<String, Object> map = new HashMap<String, Object>();

	Pattern namePattern = Pattern.compile("(?m)^(\\d\\d:\\d\\d:\\d\\d)");
	Pattern timePattern = Pattern.compile("(?m)^(\\d\\d:[0-9]{1,2}:\\d\\d)");
	Matcher matcher;

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

	public Map<String, Object> loadInfos(URL url) throws IOException {
		List<String> finalList = new ArrayList<String>();
		if (!url.toString().contains("newpage")) {
			map.clear();
			Document doc = Jsoup.connect(url.toString()).get();
			getItemTableInfo(doc);
			getItemDesciption(doc);
			getItemInfo2(doc);

			// for (String s : result) {
			// String replace = s.replaceAll("[^\\x20-\\x7e]", "");
			// String replaceAll = replace.replaceAll("-", "");
			// finalList.add(replaceAll);
			// }
			return map;
		}
		return map;
	}

	private void getItemTableInfo(Document doc) throws IOException {
		int counter = 0;

		boolean testRun = testRun(doc);
		if (testRun == true) {
			Elements table = doc.select("table");
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (!tds.isEmpty()) {
					System.err.println(tds);
					System.err.println(tds.text());
					if (counter == 0) {
						map.put("name", tds.text());
					} else if (counter == 1) {
						map.put("rank", tds.text());
					} else if (counter == 2) {
						map.put("craftingCost", tds.text());
					} else if (counter == 3) {
						map.put("craftingTime", tds.text());
					} else if ((counter == 4) && tds.contains("Xp")) {
						map.put("craftingXp", tds.text());
					} else if (counter == 5) {
						map.put("produced", tds.text());
					} else if (counter == 6) {
						map.put("goldValue", tds.text());
					}
					counter++;
					if (map.get("pic") == null) {
						Elements img = tds.select("img");
						String string = img.toString();
						String[] split = string.split("\"");
						if (split.length > 1) {
							loadPic(split[1], split[3]);
							map.put("pic", split[3]);
						}
					}

				}
				System.err.println(map);
			}
		} else {
			Elements table = doc.select("table");
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (!tds.isEmpty()) {
					System.err.println(tds.text());
					if (counter == 0) {
						map.put("name", tds.text());
					} else if (counter == 1) {
						map.put("rank", tds.text());
					} else if (counter == 2) {
						map.put("craftingCost", tds.text());
					} else if (counter == 4) {
						map.put("craftingXp", tds.text());
					} else if (counter == 5) {
						map.put("produced", tds.text());
					} else if (counter == 6) {
						map.put("goldValue", tds.text());
					}
					counter++;
					if (map.get("pic") == null) {
						Elements img = tds.select("img");
						String string = img.toString();
						String[] split = string.split("\"");
						if (split.length > 1) {
							loadPic(split[1], split[3]);
							map.put("pic", split[3]);
						}
						System.err.println(map);
					}
				}
			}
		}
	}

	private boolean testRun(Document doc) {
		Elements table = doc.select("table");
		for (Element row : table.select("tr")) {
			Elements tds = row.select("td");
			if (!tds.isEmpty()) {
				matcher = timePattern.matcher(tds.text());
				boolean find = matcher.find();
				if (find == true) {
					return true;
				}
			}
		}
		return false;

	}

	private void getItemDesciption(Document doc) {
		Elements desc = doc.select("div#page-content");
		for (Element element : desc.select("p")) {
			// result.add(element.text());
		}
	}

	private void getItemInfo2(Document doc) {
		Elements found = doc.select("div#page-content");
		for (Element ul : found.select("ul")) {
			// for (Element li : ul.select("li")){
			// }

			// result.add(li.text());
		}
	}

}
