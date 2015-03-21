package rank;

import gnu.getopt.Getopt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 
 * Source: http://xpo6.com/wp-content/uploads/2015/01/stop-word-list.txt
 * http://stackoverflow.com/questions/4716503/best-way-to-read-a-text-file
 * http://tutorials.jenkov.com/java-collections/set.html
 *
 */

public class IndexerRankerApp {
	public static void main(String args[]) {
		String metaJsonFile = "metadata2.json";
		String stopWordFile = "words.txt";
		Set<String> setSW = new HashSet<String>();
		String indexName = "index.json";
		String rankingName = "ranking.json";

		Storage store = new Storage(indexName, rankingName);
		Ranking rank = new Ranking();
		PageRanker ranker = new PageRanker();
		Indexer idx = new Indexer();
		Set<CrawledLink> allLinks = null;
		Map<String, HashMap<String, Float>> wordIndex = new HashMap<String, HashMap<String, Float>>();

		Getopt g = new Getopt("testprog", args, "c:s:i:r:");
		int k;
		String arg;

		while ((k = g.getopt()) != -1) {
			switch (k) {
			case 'c':
				arg = g.getOptarg();
				System.out.println("You picked " + (char) k + " with argument "
						+ ((arg != null) ? arg : "null"));
				if (arg != null)
					metaJsonFile = arg;
				break;
			case 's':
				arg = g.getOptarg();
				System.out.println("You picked " + (char) k + " with argument "
						+ ((arg != null) ? arg : "null"));
				if (arg != null)
					stopWordFile = arg;
				break;
			case 'i':
				arg = g.getOptarg();
				System.out.println("You picked " + (char) k + " with argument "
						+ ((arg != null) ? arg : "null"));
				if (arg != null)
					indexName = arg;
				break;
			case 'r':
				arg = g.getOptarg();
				System.out.println("You picked " + (char) k + " with argument "
						+ ((arg != null) ? arg : "null"));
				if (arg != null)
					rankingName = arg;
				break;
			case '?':
				break; // getopt() already printed an error
			default:
				System.out.print("getopt() returned " + k + "\n");
			}
		}

		try {
			BufferedReader bR = new BufferedReader(new FileReader(stopWordFile));
			String line = bR.readLine();

			while (line != null) {
				// System.out.println(line);
				// list of stop words
				setSW.add(line);
				line = bR.readLine();
			}

			bR.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// get all the crawled links
		allLinks = store.readJSON(metaJsonFile);

		List<CrawledLink> linksList = new ArrayList<CrawledLink>(allLinks);

		/**
		 * INDEXING
		 */
		// indexing words
		wordIndex = idx.wordIndexing(setSW, linksList);
		//
		// //store wordIndex to json file
		store.storeIndex(wordIndex);

		/**
		 * RANKING
		 */
		Map<String, Float> rankings = ranker.rankPages(allLinks);
		Map<String, Float> ourRanking = rank.addedRanking(allLinks);
		Map<String, RankVector> combinedRanking = new HashMap<String, RankVector>();
		String url = "";

		System.out.println("RANKINGS" + rankings.size());
		System.out.println("OUR RANKINGS" + ourRanking.size());

		for (int i = 0; i < linksList.size(); i++) {
			url = linksList.get(i).getLinkURL();

			if (ourRanking.containsKey(url) && rankings.containsKey(url)) {
				combinedRanking.put(url, new RankVector(rankings.get(url),
						ourRanking.get(url)));
			}

		}

		for (Entry<String, RankVector> entry : combinedRanking.entrySet()) {
			System.out.println(entry.getKey() + ": "
					+ entry.getValue().toString());
		}

		store.storeRanking(combinedRanking);

		// rank.wordCalculation();
		// rank.addedRanking();
	}
}
