package rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

/**
 * Source:
 * http://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
 * http://www.programcreek.com/2013/03/java-sort-map-by-value/
 * http://www.theserverside.com/news/thread.tss?thread_id=29569
 * http://www.mkyong.com/java/how-to-sort-an-arraylist-in-java/
 * http://docs.oracle.com/javase/tutorial/collections/interfaces/queue.html
 * http://stackoverflow.com/questions/1694751/java-array-sort-descending
 * http://stackoverflow.com/questions/3962766/java-reverse-list
 */

public class Ranking {
	public void pageRanking(Set<CrawledLink> allLinks) {

	}

	public void wordCalculation() {

	}

	//rank by the number of words a document has
	public Map<String, Double> addedRanking(Set<CrawledLink> allLinks) {

		Map<String, Integer> tempMap = new HashMap<String, Integer>();
		Map<String, Double> urlNnumWords = new HashMap<String, Double>();
		//Queue<Map.Entry<String, Integer>> arrangedWL = new LinkedList<Map.Entry<String, Integer>>();

		for (CrawledLink link : allLinks) {
			if (link.getWordSet() != null) {
				tempMap.put(link.getLinkURL(), link.getWordSet().size());
			}
		}

		//		for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
		//			System.out.println(entry.getKey() + ": " + entry.getValue());
		//		}
		//		System.out.println(Collections.max(tempMap.values()));

		for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
			urlNnumWords.put(entry.getKey(), ((double)entry.getValue()/Collections.max(tempMap.values())));
		}

		for (Entry<String, Double> entry : urlNnumWords.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}


		/**
		List<Integer> listVals = new ArrayList<Integer>(numWords.values());
		Collections.sort(listVals);
		Collections.reverse(listVals);

		for (Object i : listVals) {
			for (Map.Entry<String, Integer> entry : numWords.entrySet()) {
				//System.out.println(entry.getKey() + ": " + entry.getValue());
				if (entry.getValue().equals(i)) {
					arrangedWL.add(entry);
				}
			}
		}

		for (Map.Entry<String, Integer> eachEntry : arrangedWL) {
			System.out.println(eachEntry);
		}
		 **/


		//For testing purposes 
		//Storage save = new Storage("index.json", "ranking.json");
		//save.storeRank(urlNnumWords);
		
		return urlNnumWords;
	}
}
