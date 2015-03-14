package rank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Source:
 * http://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
 * 
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
		
		Map<String, Integer> numWords = new HashMap<String, Integer>();
		Map<String, Integer> arrangedWL = new HashMap<String, Integer>();
		
		for (CrawledLink link : allLinks) {
			if (link.getWordSet() != null) {
				numWords.put(link.getLinkURL(), link.getWordSet().size());
			}
		}
		
		
		for (Map.Entry<String, Integer> entry : numWords.entrySet()) {
			
		}
		
	}
}
