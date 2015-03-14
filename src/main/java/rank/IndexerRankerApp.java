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
 * Source:
 * http://xpo6.com/wp-content/uploads/2015/01/stop-word-list.txt
 * http://stackoverflow.com/questions/4716503/best-way-to-read-a-text-file
 * http://tutorials.jenkov.com/java-collections/set.html
 *
 */

public class IndexerRankerApp {
	public static void main(String args[]) {
		String metaJsonFile = "C:\\Users\\tranw_000\\Desktop\\cs454SE\\extraction\\metadata2.json";
		String stopWordFile = "C:\\Users\\tranw_000\\Desktop\\cs454SE\\rank\\words.txt";
		Set<String> setSW = new HashSet<String>();
		String indexName = "index.json";
		String rankingName = "ranking.json";
		
		Storage store = new Storage(indexName, rankingName);
		Ranking rank = new Ranking();
		Indexer idx = new Indexer();
		Set<CrawledLink> allLinks = null;
		Set<CrawledLink> allLinksHTML = new HashSet<CrawledLink>();
		Map<String, HashMap<String, Integer>> wordIndex = new HashMap<String, HashMap<String, Integer>>();
		
		Getopt g = new Getopt("testprog", args, "c:s:i:r:");
		int k;
		String arg;
		
		while ((k = g.getopt()) != -1)
		{
			switch(k)
			{
			case 'c':
				arg = g.getOptarg();
				System.out.println("You picked " + (char)k + " with argument " + ((arg != null) ? arg : "null"));
				if (arg != null) metaJsonFile = arg;
				break;
			case 's':
				arg = g.getOptarg();
				System.out.println("You picked " + (char)k + " with argument " + ((arg != null) ? arg : "null"));
				if(arg != null) stopWordFile = arg;
				break;
			case 'i':
				arg = g.getOptarg();
				System.out.println("You picked " + (char)k + " with argument " + ((arg != null) ? arg : "null"));
				if(arg != null) indexName = arg;
				break;
			case 'r':
				arg = g.getOptarg();
				System.out.println("You picked " + (char)k + " with argument " + ((arg != null) ? arg : "null"));
				if(arg != null) rankingName = arg;
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

			while(line != null) {
				//System.out.println(line);
				//list of stop words
				setSW.add(line);
				line = bR.readLine();
			}

			bR.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch  (IOException e) {
			e.printStackTrace();
		}

		//get all the crawled links
		allLinks = store.readJSON(metaJsonFile);
		
		for(CrawledLink link: allLinks){
			if(link.getLocalPath().contains(".html")){
				allLinksHTML.add(link);
			}
		}
		
		
		System.out.println("actual links: " + allLinksHTML.size());

		
		/**
		 * INDEXING
		 */
//		//indexing words
//		wordIndex = idx.wordIndexing(setSW, allLinks);
		wordIndex = idx.wordIndexing(setSW, allLinksHTML);
//		
//		//store wordIndex to json file
//		store.storeIndex(wordIndex);
		
		
		/**
		 * RANKING
		 */
		List<CrawledLink> linksList = new ArrayList<CrawledLink>(allLinksHTML);
		Double [] ranks = new PageRanker().rankPages(linksList);
		
		rank.pageRanking(allLinksHTML);
		rank.wordCalculation();
		rank.addedRanking(allLinksHTML, wordIndex);
		
//		List<CrawledLink> linksList = new ArrayList<CrawledLink>(allLinks);
//		Double [] ranks = new PageRanker().rankPages(linksList);
//		
//		rank.pageRanking(allLinks);
//		rank.wordCalculation();
//		rank.addedRanking(allLinks, wordIndex);
		
		for(int i = 0; i< ranks.length; i++){
			System.out.println(i + ", Rank: " + ranks[i] + ", URL: " + linksList.get(i).getLinkURL());
			
		}
		
		List<HashMap<String, Float>> wordValues = new TFID().tfIdfCalculator(linksList, wordIndex.keySet());
		
			System.out.println(linksList.get(0).getLinkURL());
			for (Entry<String, Float> entry : wordValues.get(0).entrySet()) {
				System.out.println(entry.getKey() + " , " + entry.getValue());
			}

		
		
	}
}
