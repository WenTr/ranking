package rank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Source:
 * http://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
 * http://www.programcreek.com/2013/03/java-sort-map-by-value/
 */

public class Ranking {
	public void pageRanking(Set<CrawledLink> allLinks) {

	}

	public void wordCalculation() {

	}

	public void addedRanking(Set<CrawledLink> allLinks, Map<String, HashMap<String, Integer>> wordIndex) {
		//rank by the number of words a document has
		//System.out.println(allLinks.toString());
		//System.out.println(allLinks.size());
		//System.out.println(wordIndex.size());
		
		Map<Integer, String> numWords = new TreeMap<Integer, String>();
		//Map<String, Integer> arrangedWL = new HashMap<String, Integer>();
	
		for (CrawledLink link : allLinks) {
			if (link.getWordSet() != null) {
				numWords.put(link.getWordSet().size(), link.getLinkURL());
			}
		}
		
		//System.out.println(numWords.toString());
		
		for (Map.Entry<Integer, String> entry : numWords.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		
//		Map<Integer, String> arrangedWL = new TreeMap<Integer, String>(numWords);
//		System.out.println();
//		System.out.println(arrangedWL.toString());
	}
}
