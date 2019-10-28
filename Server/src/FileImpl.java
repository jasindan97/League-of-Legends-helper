
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FileImpl extends UnicastRemoteObject implements FileInterface {
	private String picsFolder = "C:/Users/jasin/Desktop/Dist_assignment2/Server/src/pics/";
	private String name;

	public FileImpl(String s) throws RemoteException {
		super();
		name = s;
	}

	/**
	 * Gets String fileName as parameter from client, Looks for picture with
	 * specified path, sends file to client.
	 * @return (buffer)
	 */
	public byte[] downloadFile(String fileName) {

		try {
			System.out.println(picsFolder + fileName);
			System.out.println(super.getClientHost());
			File file = new File(picsFolder + fileName + ".jpg");
			byte buffer[] = new byte[(int) file.length()];
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(picsFolder + fileName + ".jpg"));
			input.read(buffer, 0, buffer.length);
			input.close();
			return (buffer);
		} catch (Exception e) {
			System.out.println("FileImpl: " + e.getMessage());
			e.printStackTrace();
			return (null);
		}

	}

	/**
	 * Save url as String Use Jsoup to connect to the website using url Webscrape
	 * data for champion roles and save as string
	 * 
	 * @return combine
	 */
	public String champRolez() {
		final String url = "https://na.op.gg/champion/statistics";
		String combine = "";
		try {
			final Document document = Jsoup.connect(url).get();

			for (Element row : document.select("div.champion-index__champion-item")) {
				if (row.select(".champion-index__champion-item__name").text().equals("")) {
					continue;
				} else {
					final String champName = row.select(".champion-index__champion-item__name").text();
					final String positionName = row.select(".champion-index__champion-item__positions").text();
					System.out.println(champName + ": " + positionName.replace(" ", ", "));
					System.out.println();
					combine += champName + ": " + positionName.replace(" ", ", ") + "\n";
				}
			}
			return combine;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return combine;
	}

	/**
	 * Save url as String Use Jsoup to connect to the website using url Webscraped
	 * data for every champions build, save webscraped data to ArrayList String
	 * champName (will be passed in by Client) used to modify url for each champion.
	 * 
	 * @return items
	 */
	public ArrayList<String> championBuildz(String champName, ArrayList<String> items) {
		items = new ArrayList<String>(6);
		final String url = "https://rankedboost.com/league-of-legends/build/" + champName;

		try {
			final Document document = Jsoup.connect(url).get();
			Elements elements = document.getElementsByClass("rb-build-spells");
			Elements build = elements.last().getElementsByAttribute("title");
			for (Element item : build) {
				items.add(item.attributes().get("title"));
			}
			System.out.println(items);
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			return items;
		}
	}

	/**
	 * Save url as String Use Jsoup to connect to the website using url Scrape
	 * updated player match history Save Webscraped data to ArrayLists
	 * 
	 * @return matches
	 */
	public ArrayList<String> matchHistory(String summonerName) {
		ArrayList<String> matches = new ArrayList<String>();
		ArrayList<String> champNamesText = new ArrayList<String>();
		ArrayList<String> kdasText = new ArrayList<String>();
		ArrayList<String> winRatiosText = new ArrayList<String>();

		final String url = "https://na.op.gg/summoner/userName=" + summonerName;

		try {
			final Document document = Jsoup.connect(url).get();
			Elements champNames = document.getElementsByClass("ChampionName");

			for (Element item : champNames) {
				champNamesText.add(item.getElementsByTag("a").text());
			}
			Iterator<String> champNamesIterator = champNamesText.iterator();

			Elements kdas = document.getElementsByClass("KDA");

			for (Element item : kdas) {
				kdasText.add(item.getElementsByAttribute("title").text());
			}
			Iterator<String> kdaIterator = kdasText.iterator();

			Elements winRatios = document.getElementsByClass("WinRatio");

			for (Element item : winRatios) {
				winRatiosText.add(item.getElementsByAttribute("title").text());
			}
			Iterator<String> wrIterator = winRatiosText.iterator();
			System.out.println(champNamesText.size() + " " + kdasText.size() + " " + winRatiosText.size());
			while (kdaIterator.hasNext() && champNamesIterator.hasNext() && wrIterator.hasNext()) {
				matches.add(champNamesIterator.next() + "--" + kdaIterator.next() + "--" + wrIterator.next());
			}
			System.out.println(matches);
			return matches;
		} catch (Exception e) {
			e.printStackTrace();
			return matches;
		}
	}

	/**
	 * Save url as String Use Jsoup to connect to the website using url Scrape
	 * Updated LCS Team statistics
	 * 
	 * @return sb.toString()
	 */
	public String teamStats() {

		StringBuilder sb = new StringBuilder("Team Name | Games Played | Games Won | Games Lost \n");
		final String url = "https://oracleselixir.com/statistics/na/lcs-2019-spring-regular-season-team-statistics/";

		try {
			final Document document = Jsoup.connect(url).get();

			for (Element row : document.select("tbody.row-hover")) {
				if (row.select(".column-1").text().equals("")) {
					continue;
				} else {
					String[] teamNames = row.select(".column-1").html().split("\n");
					String[] gamesPlayed = row.select(".column-2").html().split("\n");
					String[] gamesWon = row.select(".column-3").html().split("\n");
					String[] gamesLost = row.select(".column-4").html().split("\n");

					for (int i = 0; i < teamNames.length; i++) {
						sb.append(teamNames[i] + " | " + gamesPlayed[i] + " | " + gamesWon[i] + " | " + gamesLost[i]
								+ "\n");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Save url as String Use Jsoup to connect to the website using url Gets
	 * champion counters (champion counters changes every other week depending on
	 * patch releases)
	 * 
	 * @return sb.toString()
	 */
	public String champCounters(String champName) {
		StringBuilder sb = new StringBuilder("-- Counter Pick --\n");
		final String url = "https://www.leagueofgraphs.com/champions/counters/" + champName;

		try {
			final Document document = Jsoup.connect(url).get();

			for (Element row : document.select("div.box.box-padding")) {
				if (row.select(".box-title").text().equals("")) {
					continue;
				} else {
					String[] champNames = row.select(".name").html().split("\n");
					String[] wr = row.select(".progressBarTxt").html().split("\n");
					String[] gd = row.select(".name").html().split("\n");

					for (int i = 0; i < champNames.length; i++) {
						sb.append("      " + champNames[i]);
						sb.append(wr[i] + "\n");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}